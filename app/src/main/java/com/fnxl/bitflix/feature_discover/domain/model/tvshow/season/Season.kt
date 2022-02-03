package com.fnxl.bitflix.feature_discover.domain.model.tvshow.season

data class Season(
    val _id: String = "",
    val air_date: String = "",
    val episodes: List<Episode> = emptyList(),
    val id: Int = Int.MIN_VALUE,
    val name: String = "" ,
    val overview: String = "" ,
    val poster_path: String = "",
    val season_number: Int = Int.MIN_VALUE
)