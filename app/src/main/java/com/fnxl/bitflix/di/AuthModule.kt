package com.fnxl.bitflix.di

import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.feature_auth.data.remote.AuthApi
import com.fnxl.bitflix.feature_auth.data.repository.AuthRepositoryImpl
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository
import com.fnxl.bitflix.feature_auth.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Config.SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(api: AuthApi, dataStore: DataStorePreferences): AuthRepository {
        return AuthRepositoryImpl(api, dataStore)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetTokenUseCase(repository: AuthRepository): GetTokenUseCase {
        return GetTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideCheckSessionUseCase(repository: AuthRepository): CheckSessionUseCase {
        return CheckSessionUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }
}
