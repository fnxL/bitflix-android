package com.fnxl.bitflix.feature_auth.data.remote.request

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
    val inviteKey: String,
)