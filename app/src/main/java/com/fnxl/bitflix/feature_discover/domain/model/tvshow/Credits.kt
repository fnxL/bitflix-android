package com.fnxl.bitflix.feature_discover.domain.model.tvshow

data class Credits(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList()
)