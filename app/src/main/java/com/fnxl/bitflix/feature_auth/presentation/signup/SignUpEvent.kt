package com.fnxl.bitflix.feature_auth.presentation.signup

sealed class SignUpEvent {
    data class OnFirstNameChange(val firstName: String) : SignUpEvent()
    data class OnLastNameChange(val lastName: String) : SignUpEvent()
    data class OnEmailChange(val email: String) : SignUpEvent()
    data class OnUsernameChange(val username: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    data class OnConfirmPasswordChange(val confirmPassword: String) : SignUpEvent()
    data class OnInviteKeyChange(val inviteKey: String) : SignUpEvent()
    object OnRegister : SignUpEvent()
    object OnLoginClick : SignUpEvent()
}