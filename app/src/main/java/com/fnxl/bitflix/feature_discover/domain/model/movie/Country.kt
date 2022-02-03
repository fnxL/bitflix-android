package com.fnxl.bitflix.feature_discover.domain.model.movie

data class Country(
    val certification: String,
    val iso_3166_1: String,
    val primary: Boolean,
    val release_date: String
)