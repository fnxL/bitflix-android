package com.fnxl.bitflix.feature_discover.presentation.search

import androidx.paging.PagingData
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SearchState(
    var searchText: String = "",
    var loading: Boolean = false,
    val error: String = "",
    val titleVisited: Boolean = false,
)
