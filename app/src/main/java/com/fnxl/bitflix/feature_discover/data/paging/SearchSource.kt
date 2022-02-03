package com.fnxl.bitflix.feature_discover.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.feature_discover.data.remote.TmdbApi
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem

class SearchSource(
    private val api: TmdbApi,
    private val query: String,
) : PagingSource<Int, ResultItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultItem>): Int? {
        TODO("Not yet implemented")
    }

    private var currentPage = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultItem> {
        return try {
            val nextPage = params.key ?: currentPage
            val result = api.searchItem(query = query, page = nextPage)
            LoadResult.Page(
                data = result.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (result.total_pages == nextPage) null else currentPage + 1
            ).also { currentPage++ }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}