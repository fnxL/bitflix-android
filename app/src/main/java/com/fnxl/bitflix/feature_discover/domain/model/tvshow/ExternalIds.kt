package com.fnxl.bitflix.feature_discover.domain.model.tvshow

data class ExternalIds(
    val facebook_id: String = "",
    val freebase_id: String = "",
    val freebase_mid: Any = "",
    val imdb_id: String = "",
    val instagram_id: String = "",
    val tvdb_id: Int = Int.MIN_VALUE,
    val tvrage_id: Any= "",
    val twitter_id: String= ""
)