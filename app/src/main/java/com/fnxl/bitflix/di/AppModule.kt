package com.fnxl.bitflix.di

import android.content.Context
import com.fnxl.bitflix.core.data.DataStorePreferencesImpl
import com.fnxl.bitflix.core.data.remote.UpdateApi
import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.domain.usecase.CheckUpdateUseCase
import com.fnxl.bitflix.core.networking.AuthInterceptor
import com.fnxl.bitflix.core.networking.TokenAuthenticator
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.feature_auth.domain.usecase.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val CONNECTION_TIMEOUT = 60L

    @Singleton
    @Provides
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStorePreferences {
        return DataStorePreferencesImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideTokenAuthenticator(
        dataStore: DataStorePreferences,
        getTokenUseCase: GetTokenUseCase
    ): TokenAuthenticator {
        return TokenAuthenticator(dataStore = dataStore, getTokenUseCase = getTokenUseCase)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authenticator: TokenAuthenticator,
        dataStore: DataStorePreferences
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(AuthInterceptor(dataStore = dataStore))
        okHttpClientBuilder.authenticator(authenticator)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideUpdateApi(): UpdateApi {
        return Retrofit.Builder()
            .baseUrl(Constants.UPDATE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(UpdateApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCheckUpdateUseCase(api: UpdateApi): CheckUpdateUseCase {
        return CheckUpdateUseCase(api = api)
    }
}