package com.fnxl.bitflix.feature_discover.domain.model.movie

data class Credits(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList()
)