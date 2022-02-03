package com.fnxl.bitflix.feature_discover.domain.repository

import androidx.paging.PagingData
import com.fnxl.bitflix.core.domain.models.RowData
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Season
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    suspend fun getRowData(rowData: RowData, mediaType: MediaType): Resource<List<ResultItem>>

    suspend fun getTrendingList(mediaType: MediaType): Resource<List<ResultItem>>

    suspend fun getMovieDetails(id: String, append: String): Resource<Movie>

    suspend fun getShowDetails(id: String, append: String): Resource<TVShow>

    suspend fun getSeasonDetails(id: String, seasonNumber: String): Resource<Season>

    fun searchTitles(query: String) : Flow<PagingData<ResultItem>>

}