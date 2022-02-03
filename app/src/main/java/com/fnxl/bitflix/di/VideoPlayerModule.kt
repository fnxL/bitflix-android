package com.fnxl.bitflix.di

import android.content.Context
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.core.util.Config.SERVER_BASE_URL
import com.fnxl.bitflix.core.videoplayer.VLCMediaPlayer
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.videolan.libvlc.LibVLC
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoPlayerModule {
    @Provides
    fun provideLibVLC(@ApplicationContext context: Context): LibVLC {
        val options = ArrayList<String>().apply {
            add("-vvv")
            add("--freetype-bold")
        }
        return LibVLC(context, options)
    }

    @Provides
    fun provideVLCMediaPlayer(libVLC: LibVLC): VLCMediaPlayer {
        return VLCMediaPlayer(libVLC)
    }
}
