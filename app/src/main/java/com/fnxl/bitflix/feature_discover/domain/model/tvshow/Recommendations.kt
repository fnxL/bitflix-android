package com.fnxl.bitflix.feature_discover.domain.model.tvshow

import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem

data class Recommendations(
    val page: Int = Int.MIN_VALUE,
    val results: List<ResultItem> = emptyList(),
    val total_pages: Int = Int.MIN_VALUE,
    val total_results: Int = Int.MIN_VALUE
)