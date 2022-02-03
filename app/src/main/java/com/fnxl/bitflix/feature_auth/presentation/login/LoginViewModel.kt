package com.fnxl.bitflix.feature_auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.domain.states.PasswordTextFieldState
import com.fnxl.bitflix.core.domain.states.StandardTextFieldState
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.core.util.sendErrorSnackbar
import com.fnxl.bitflix.destinations.SignUpScreenDestination
import com.fnxl.bitflix.feature_auth.domain.usecase.LoginUseCase
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var username by mutableStateOf(StandardTextFieldState())
        private set

    var password by mutableStateOf(PasswordTextFieldState())
        private set

    var state by mutableStateOf(LoginState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUsernameChange -> {
                username = username.copy(text = event.username)
            }
            is LoginEvent.OnPasswordChange -> {
                password = password.copy(text = event.password)
            }
            is LoginEvent.TogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            is LoginEvent.OnSignUpClick -> {
                sendUiEvent(UiEvent.Navigate(SignUpScreenDestination))
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            val loginResult = loginUseCase(username = username.text, password = password.text)
            state = state.copy(loading = false)
            if (loginResult.usernameError != null) {
                username = username.copy(error = loginResult.usernameError)
            }
            if (loginResult.passwordError != null) {
                password = password.copy(error = loginResult.passwordError)
            }

            when (loginResult.result) {
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.OnLogin)
                }
                is Resource.Error -> {
                    _uiEvent.send(sendErrorSnackbar(loginResult.result))
                }
                else -> Unit
            }
        }
    }
}