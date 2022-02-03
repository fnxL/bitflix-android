package com.fnxl.bitflix.feature_discover.data.remote.response

import com.fnxl.bitflix.core.domain.models.VideoSource

data class StreamLinksResponse(
    val ultraHD: List<VideoSource>?,
    val fullHD: List<VideoSource>?,
    val hd: List<VideoSource>?
)
