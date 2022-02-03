package com.fnxl.bitflix.feature_auth.presentation.signup

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
import com.fnxl.bitflix.destinations.LoginScreenDestination
import com.fnxl.bitflix.feature_auth.domain.usecase.SignUpUseCase
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    var firstName by mutableStateOf(StandardTextFieldState())
        private set

    var lastName by mutableStateOf(StandardTextFieldState())
        private set

    var username by mutableStateOf(StandardTextFieldState())
        private set

    var email by mutableStateOf(StandardTextFieldState())
        private set

    var inviteKey by mutableStateOf(StandardTextFieldState())
        private set

    var password by mutableStateOf(PasswordTextFieldState())
        private set

    var confirmPassword by mutableStateOf(PasswordTextFieldState())
        private set

    var loading by mutableStateOf(false)
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnFirstNameChange -> {
                firstName = firstName.copy(text = event.firstName)
            }
            is SignUpEvent.OnLastNameChange -> {
                lastName = lastName.copy(text = event.lastName)
            }
            is SignUpEvent.OnEmailChange -> {
                email = email.copy(text = event.email)
            }
            is SignUpEvent.OnUsernameChange -> {
                username = username.copy(text = event.username)
            }
            is SignUpEvent.OnPasswordChange -> {
                password = password.copy(text = event.password)
            }
            is SignUpEvent.OnConfirmPasswordChange -> {
                confirmPassword = confirmPassword.copy(text = event.confirmPassword)
            }
            is SignUpEvent.OnInviteKeyChange -> {
                inviteKey = inviteKey.copy(text = event.inviteKey)
            }
            is SignUpEvent.OnRegister -> {
                register()
            }
            is SignUpEvent.OnLoginClick -> {
                sendUiEvent(UiEvent.Navigate(LoginScreenDestination))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun register() {
        viewModelScope.launch {

            loading = true

            val signupResult = signUpUseCase(
                firstName = firstName.text,
                lastName = lastName.text,
                email = email.text,
                username = username.text,
                password = password.text,
                confirmPassword = confirmPassword.text,
                inviteKey = inviteKey.text,
            )

            loading = false

            if (signupResult.firstNameError != null) {
                firstName = firstName.copy(error = signupResult.firstNameError)
            }
            if (signupResult.lastNameError != null) {
                lastName = lastName.copy(error = signupResult.lastNameError)
            }
            if (signupResult.emailError != null) {
                email = email.copy(error = signupResult.emailError)
            }
            if (signupResult.usernameError != null) {
                username = username.copy(error = signupResult.usernameError)
            }
            if (signupResult.passwordError != null) {
                password = password.copy(error = signupResult.passwordError)
            }
            if (signupResult.inviteKeyError != null) {
                inviteKey = inviteKey.copy(error = signupResult.inviteKeyError)
            }

            when (signupResult.result) {
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.SnackBar("Account Created Successfully!"))
                }

                is Resource.Error -> {
                    _uiEvent.send(sendErrorSnackbar(signupResult.result))
                }

                else -> Unit
            }

        }

    }


}