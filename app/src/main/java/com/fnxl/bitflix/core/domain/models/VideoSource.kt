package com.fnxl.bitflix.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoSource(
    val name: String = "",
    val size: String = "",
    val url: String = ""
) : Parcelable

