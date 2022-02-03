package com.fnxl.bitflix.feature_auth.data.repository

import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.util.NetworkError
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.SimpleResource
import com.fnxl.bitflix.feature_auth.data.remote.AuthApi
import com.fnxl.bitflix.feature_auth.data.remote.request.LoginRequest
import com.fnxl.bitflix.feature_auth.data.remote.request.RegisterRequest
import com.fnxl.bitflix.feature_auth.data.remote.request.TokenRequest
import com.fnxl.bitflix.feature_auth.data.remote.response.TokenResponse
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val dataStore: DataStorePreferences
) : AuthRepository {
    override suspend fun signup(
        request: RegisterRequest
    ): SimpleResource {
        return try {
            api.signup(request = request)
            Resource.Success(Unit)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun login(username: String, password: String): SimpleResource {
        val request = LoginRequest(username.trim(), password.trim())
        return try {
            val response = api.login(request)
            dataStore.saveAccessToken(response.accessToken)
            dataStore.saveRefreshToken(response.refreshToken)
            Resource.Success(Unit)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun logout(): SimpleResource {
        val token = dataStore.readRefreshToken().first()
        val request = TokenRequest(token = token)
        return try {
            api.logout(request = request)
            dataStore.saveAccessToken("")
            dataStore.saveRefreshToken("")
            Resource.Success(Unit)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun getToken(refreshToken: String): Resource<TokenResponse> {
        val request = TokenRequest(token = refreshToken)
        return try {
            val response = api.token(request)
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun verifyUserSession(refreshToken: String) : SimpleResource {
        val request = TokenRequest(token = refreshToken)
        return try {
            val response = api.verifySession(request = request)
            Resource.Success(response)
        } catch (e: Exception) {
             NetworkError.resolveError(e)
        }
    }
}