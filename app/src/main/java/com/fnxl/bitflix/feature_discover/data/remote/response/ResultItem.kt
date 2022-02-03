package com.fnxl.bitflix.feature_discover.data.remote.response

data class ResultItem(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val first_air_date: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = Int.MIN_VALUE,
    val media_type: String = "",
    val name: String = "",
    val origin_country: List<String> = emptyList(),
    val original_language: String = "",
    val original_name: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = Double.MIN_VALUE,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = Double.MIN_VALUE,
    val vote_count: Int = Int.MIN_VALUE
)