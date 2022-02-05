package com.fnxl.bitflix.di

import android.content.Context
import com.fnxl.bitflix.core.videoplayer.VLCMediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.videolan.libvlc.LibVLC

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
