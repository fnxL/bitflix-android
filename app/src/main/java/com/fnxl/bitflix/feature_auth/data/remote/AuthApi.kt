package com.fnxl.bitflix.feature_auth.data.remote

import com.fnxl.bitflix.feature_auth.data.remote.request.LoginRequest
import com.fnxl.bitflix.feature_auth.data.remote.request.RegisterRequest
import com.fnxl.bitflix.feature_auth.data.remote.request.TokenRequest
import com.fnxl.bitflix.feature_auth.data.remote.response.LoginResponse
import com.fnxl.bitflix.feature_auth.data.remote.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout(@Body request: TokenRequest)

    @POST("auth/signup")
    suspend fun signup(@Body request: RegisterRequest)

    @POST("auth/token")
    suspend fun token(@Body request: TokenRequest): TokenResponse

    @POST("auth/verify-session")
    suspend fun verifySession(@Body request: TokenRequest)
}
