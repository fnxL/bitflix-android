package com.fnxl.bitflix.feature_discover.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.feature_discover.data.remote.TmdbApi
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem

class DiscoverSource(
    private val api: TmdbApi,
    private val mediaType: MediaType,
    private val queryMap: Map<String, String> = emptyMap()
) : PagingSource<Int, ResultItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultItem>): Int? {
        TODO("Not yet implemented")
    }

    private var currentPage = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultItem> {
        return try {
            val nextPage = params.key ?: currentPage
            val result = when (mediaType) {
                MediaType.MOVIE -> api.discoverTitles(
                    mediaType = mediaType.value,
                    page = nextPage,
                    options = queryMap
                )
                MediaType.TVShow -> api.discoverTitles(
                    mediaType = mediaType.value,
                    page = nextPage,
                    options = queryMap
                )
            }
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