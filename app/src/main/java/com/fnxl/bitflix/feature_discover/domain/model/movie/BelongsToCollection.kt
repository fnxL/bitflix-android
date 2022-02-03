package com.fnxl.bitflix.feature_discover.domain.model.movie

data class BelongsToCollection(
    val backdrop_path: String = "",
    val id: Int = Int.MIN_VALUE,
    val name: String = "",
    val poster_path: String = ""
)