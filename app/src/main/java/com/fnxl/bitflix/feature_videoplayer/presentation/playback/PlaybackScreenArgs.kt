package com.fnxl.bitflix.feature_videoplayer.presentation.playback

import android.os.Parcelable
import com.fnxl.bitflix.core.domain.models.VideoSource
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaybackScreenArgs(
    val title: String,
    val videoSourceList: List<VideoSource>
) : Parcelable
