package com.fnxl.bitflix.feature_auth.domain.repository

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.SimpleResource
import com.fnxl.bitflix.feature_auth.data.remote.request.RegisterRequest
import com.fnxl.bitflix.feature_auth.data.remote.response.TokenResponse

interface AuthRepository {

    suspend fun signup(request: RegisterRequest): SimpleResource

    suspend fun login(username: String, password: String): SimpleResource

    suspend fun logout(): SimpleResource

    suspend fun getToken(refreshToken: String): Resource<TokenResponse>

    suspend fun verifyUserSession(refreshToken: String): SimpleResource
}