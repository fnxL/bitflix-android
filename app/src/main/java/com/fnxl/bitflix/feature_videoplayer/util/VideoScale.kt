package com.fnxl.bitflix.feature_videoplayer.util

import org.videolan.libvlc.MediaPlayer

object VideoScale {
    val scale = listOf(
        MediaPlayer.ScaleType.SURFACE_BEST_FIT to "BEST FIT",
        MediaPlayer.ScaleType.SURFACE_FIT_SCREEN to "FIT SCREEN",
        MediaPlayer.ScaleType.SURFACE_FILL to "FILL",
        MediaPlayer.ScaleType.SURFACE_16_9 to "16:9",
        MediaPlayer.ScaleType.SURFACE_4_3 to "4:3",
        MediaPlayer.ScaleType.SURFACE_ORIGINAL to "ORIGINAL"
    )
}
