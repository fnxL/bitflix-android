package com.fnxl.bitflix.feature_auth.data.remote.response

data class TokenResponse(
    val accessToken: String,
    val message: String,
    val status: String
)
