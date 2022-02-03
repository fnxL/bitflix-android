package com.fnxl.bitflix.core.networking

import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_auth.data.remote.response.TokenResponse
import com.fnxl.bitflix.feature_auth.domain.usecase.GetTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val dataStore: DataStorePreferences,
    private val getTokenUseCase: GetTokenUseCase
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val result = getToken()) {
                is Resource.Success -> {
                    // Save the new accessToken
                    dataStore.saveAccessToken(result.data!!.accessToken)
                    // Retry the request
                    response.request.newBuilder()
                        .header(
                            "Authorization",
                            "Bearer ${result.data.accessToken}"
                        ).build()
                }
                is Resource.Error -> {
                    // Logout user
                    dataStore.saveRefreshToken("")
                    null
                }
            }
        }
    }

    private suspend fun getToken(): Resource<TokenResponse> {
        val refreshToken = dataStore.readRefreshToken().first()
        return getTokenUseCase(refreshToken = refreshToken)
    }


}