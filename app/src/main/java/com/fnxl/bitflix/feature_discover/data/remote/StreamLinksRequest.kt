package com.fnxl.bitflix.feature_discover.data.remote

data class StreamLinksRequest(
    val title: String,
    val year: String,
    val seasonNumber: Number?,
    val episodeNumber: Number?,
    val type: String,
    val platform: String = "android",
)
