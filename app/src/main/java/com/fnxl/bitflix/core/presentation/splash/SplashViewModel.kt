package com.fnxl.bitflix.core.presentation.splash

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.BuildConfig
import com.fnxl.bitflix.core.data.remote.response.UpdateResponse
import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.domain.usecase.CheckUpdateUseCase
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.destinations.LoginScreenDestination
import com.fnxl.bitflix.destinations.MovieScreenDestination
import com.fnxl.bitflix.feature_auth.domain.usecase.CheckSessionUseCase
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import javax.inject.Inject

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: DataStorePreferences,
    private val checkSessionUseCase: CheckSessionUseCase,
    private val checkUpdateUseCase: CheckUpdateUseCase,
    @ApplicationContext private val context: Context,
) :
    ViewModel() {

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    var isUpdateAvailable by mutableStateOf(false)
        private set

    var isSplashCompleted by mutableStateOf(false)
        private set

    var destination by mutableStateOf<Direction?>(null)

    var state by mutableStateOf(UpdateState(update = UpdateResponse()))

    var updateDialog by mutableStateOf(false)

    var fileExists by mutableStateOf(false)

    init {
        checkUpdate()
    }

    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.OnSplashComplete -> {
                isSplashCompleted = true
                verifyUserLoginStatus()
            }
            is SplashEvent.OnDownload -> {
                updateDialog = false
                downloadUpdate()
            }
        }
    }

    private fun downloadUpdate() {
        val fileName = state.update.assets[0].name
        val url = state.update.assets[0].browser_download_url
        var destination  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/"
        destination +=fileName
        Log.d("ROUTE", "destination: $destination")
        // TODO: Install Intent
        val file = File(destination)
        if(file.exists()) {
            Log.d("ROUTE", "downloadUpdate: FileExists")
            fileExists = true
        } else {
            val request = DownloadManager.Request(Uri.parse(url))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setTitle(fileName)
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)

            val downloadManger = ContextCompat.getSystemService(context, DownloadManager::class.java)
            val downloadID = downloadManger?.enqueue(request)
            val install = Intent(Intent.ACTION_VIEW)
        }
    }

    private fun checkUpdate() {
        viewModelScope.launch {
            when(val result = checkUpdateUseCase()) {
                is Resource.Success -> {
                    if(result.data?.tag_name!! > BuildConfig.VERSION_NAME) {
                        isUpdateAvailable = true
                        updateDialog = true
                        state = state.copy(update = result.data)
                    }
                }
                else -> Unit
            }
        }
    }

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    private fun verifyUserLoginStatus() {
        viewModelScope.launch {
            val refreshToken = dataStore.readRefreshToken().first()
            if(isUpdateAvailable) return@launch
            if (refreshToken.isNotEmpty()) {
                when (val result = checkSessionUseCase(refreshToken = refreshToken)) {
                    is Resource.Success -> {
                        _eventFlow.send(UiEvent.Navigate(MovieScreenDestination))
                    }
                    is Resource.Error -> {
                        // Logout when there is not a connection error
                        if(result.exception?.errorCode != -1) {
                            dataStore.saveAccessToken("")
                            dataStore.saveRefreshToken("")
                        }
                    }
                }
            } else {
                return@launch _eventFlow.send(UiEvent.Navigate(LoginScreenDestination))
            }
        }
    }

}