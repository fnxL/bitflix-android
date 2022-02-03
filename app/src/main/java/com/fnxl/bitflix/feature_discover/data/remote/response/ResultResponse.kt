package com.fnxl.bitflix.feature_discover.data.remote.response


data class ResultResponse(
    val page: Int,
    val results: List<ResultItem>,
    val total_pages: Int,
    val total_results: Int
)