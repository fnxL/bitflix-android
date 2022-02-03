package com.fnxl.bitflix.feature_auth.presentation.login

data class LoginState(
    val loading: Boolean = false,
    val isPasswordVisible: Boolean = false
)