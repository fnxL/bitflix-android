package com.fnxl.bitflix.di

import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.core.util.Config.SERVER_BASE_URL
import com.fnxl.bitflix.feature_discover.data.remote.BitflixApi
import com.fnxl.bitflix.feature_discover.data.remote.TmdbApi
import com.fnxl.bitflix.feature_discover.data.repository.BitflixRepositoryImpl
import com.fnxl.bitflix.feature_discover.data.repository.DiscoverRepositoryImpl
import com.fnxl.bitflix.feature_discover.domain.repository.BitflixRepository
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository
import com.fnxl.bitflix.feature_discover.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverModule {

    @Provides
    @Singleton
    fun provideTmdbApi(): TmdbApi {
        return Retrofit.Builder()
            .baseUrl(Config.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }


    @Provides
    @Singleton
    fun provideBitflixApi(client: OkHttpClient): BitflixApi {
        return Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BitflixApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDiscoverRepository(api: TmdbApi): DiscoverRepository {
        return DiscoverRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideBitflixRepository(api: BitflixApi): BitflixRepository {
        return BitflixRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideDiscoverUseCases(
        repository: DiscoverRepository,
        bitflixRepository: BitflixRepository
    ): DiscoverUseCases {
        return DiscoverUseCases(
            getRowData = GetRowDataUseCase(repository),
            getTrending = GetTrendingUseCase(repository),
            getMovieDetails = GetMovieDetailsUseCase(repository),
            getSeasonDetails = GetSeasonDetailsUseCase(repository),
            getShowDetails = GetShowDetailsUseCase(repository),
            getStreamLinksUseCase = GetStreamLinksUseCase(repository = bitflixRepository),
            searchTitleUseCase = SearchTitleUseCase(repository = repository)
        )
    }
}
