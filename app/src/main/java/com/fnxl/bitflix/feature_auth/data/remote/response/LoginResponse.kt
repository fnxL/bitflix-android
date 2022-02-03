package com.fnxl.bitflix.feature_auth.data.remote.response

import com.fnxl.bitflix.core.domain.models.User

data class LoginResponse(
    val accessToken: String,
    val message: String,
    val refreshToken: String,
    val status: String,
    val userData: User
)
