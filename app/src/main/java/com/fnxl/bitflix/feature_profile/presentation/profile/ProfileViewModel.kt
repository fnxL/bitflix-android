package com.fnxl.bitflix.feature_profile.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.feature_auth.domain.usecase.LogoutUseCase
import com.fnxl.bitflix.feature_discover.data.remote.BitflixApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : ViewModel() {


    var state by mutableStateOf(ProfileState())
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProfileEvent.OnLogout -> {
                logout()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}