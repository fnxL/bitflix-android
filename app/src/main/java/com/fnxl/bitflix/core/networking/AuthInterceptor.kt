package com.fnxl.bitflix.core.networking


import com.fnxl.bitflix.core.domain.DataStorePreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor(private val dataStore: DataStorePreferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStore.readAccessToken().first()
        }
        val refreshToken = runBlocking {
            dataStore.readRefreshToken().first()
        }
        val originalRequest = chain.request()
        val authorizedRequestBuilder =
            originalRequest.newBuilder().addHeader("Authorization", "Bearer $accessToken").addHeader("x-refresh-token", refreshToken)
        return chain.proceed(authorizedRequestBuilder.build())
    }
}