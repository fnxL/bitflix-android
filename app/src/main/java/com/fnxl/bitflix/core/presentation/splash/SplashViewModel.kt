package com.fnxl.bitflix.core.presentation.splash

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.util.Event
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.destinations.LoginScreenDestination
import com.fnxl.bitflix.destinations.MovieScreenDestination
import com.fnxl.bitflix.feature_auth.domain.usecase.CheckSessionUseCase
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: DataStorePreferences,
) :
    ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    fun checkLoggedIn() {
        Timber.d("ROUTE: Checking Login Activity")
        checkUserLoginActivity()
    }

    @ExperimentalPagerApi
    @ExperimentalComposeUiApi
    private fun checkUserLoginActivity() {
        viewModelScope.launch {
            dataStore.saveSplashCompleted(true)
            val refreshToken = dataStore.readRefreshToken().first()
            if (refreshToken.isEmpty()) {
                Timber.d("Route checkUserLoginActivity Token is Empty")
                return@launch _eventFlow.emit(
                    UiEvent.Navigate(
                        LoginScreenDestination
                    )
                )
            } else {
                _eventFlow.emit(UiEvent.Navigate(MovieScreenDestination))
            }
        }
    }
}