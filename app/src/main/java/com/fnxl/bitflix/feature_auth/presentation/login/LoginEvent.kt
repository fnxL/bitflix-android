package com.fnxl.bitflix.feature_auth.presentation.login

sealed class LoginEvent {
    object Login : LoginEvent()
    object TogglePasswordVisibility: LoginEvent()
    object OnSignUpClick : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data class OnUsernameChange(val username: String) : LoginEvent()
}