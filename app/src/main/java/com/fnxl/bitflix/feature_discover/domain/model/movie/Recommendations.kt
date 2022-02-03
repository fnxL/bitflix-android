package com.fnxl.bitflix.feature_discover.domain.model.movie

import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem

data class Recommendations(
    val page: Int = Int.MAX_VALUE,
    val results: List<ResultItem> = emptyList(),
    val total_pages: Int = Int.MAX_VALUE,
    val total_results: Int = Int.MAX_VALUE
)