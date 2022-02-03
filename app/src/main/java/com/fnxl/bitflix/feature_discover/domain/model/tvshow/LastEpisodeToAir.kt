package com.fnxl.bitflix.feature_discover.domain.model.tvshow

data class LastEpisodeToAir(
    val air_date: String = "",
    val episode_number: Int = Int.MIN_VALUE,
    val id: Int = Int.MIN_VALUE,
    val name: String = "",
    val overview: String = "",
    val production_code: String = "",
    val season_number: Int = Int.MIN_VALUE,
    val still_path: String = "",
    val vote_average: Double = Double.MIN_VALUE,
    val vote_count: Int = Int.MIN_VALUE
)