package com.fnxl.bitflix.feature_discover.domain.model.movie

data class Images(
    val backdrops: List<Backdrop> = emptyList(),
    val logos: List<Logo> = emptyList(),
    val posters: List<Poster> = emptyList()
)