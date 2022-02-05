package com.fnxl.bitflix.core.data.remote.response

data class Asset(
    val browser_download_url: String,
    val content_type: String,
    val created_at: String,
    val download_count: Int,
    val name: String,
    val size: Int,
    val updated_at: String,
)