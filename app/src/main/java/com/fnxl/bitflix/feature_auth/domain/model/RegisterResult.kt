package com.fnxl.bitflix.feature_auth.domain.model

import com.fnxl.bitflix.core.util.SimpleResource
import com.fnxl.bitflix.feature_auth.presentation.util.AuthError

data class RegisterResult(
    val firstNameError: AuthError? = null,
    val lastNameError: AuthError? = null,
    val inviteKeyError: AuthError? = null,
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)