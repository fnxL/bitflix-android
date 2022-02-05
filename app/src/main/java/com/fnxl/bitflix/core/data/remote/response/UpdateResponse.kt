package com.fnxl.bitflix.core.data.remote.response

data class UpdateResponse(
    val assets: List<Asset> = emptyList(),
    val body: String = "",
    val created_at: String = "",
    val id: Int = 0,
    val name: String = "",
    val published_at: String = "",
    val tag_name: String = "",
)