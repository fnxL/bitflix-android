package com.fnxl.bitflix.feature_auth.domain.model

import com.fnxl.bitflix.core.util.SimpleResource
import com.fnxl.bitflix.feature_auth.presentation.util.AuthError

data class LoginResult(
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
