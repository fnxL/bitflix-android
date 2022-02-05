package com.fnxl.bitflix.feature_discover.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.fnxl.bitflix.core.domain.models.RowData
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.NetworkError
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.paging.SearchSource
import com.fnxl.bitflix.feature_discover.data.remote.TmdbApi
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Season
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow

class DiscoverRepositoryImpl(private val api: TmdbApi) :
    DiscoverRepository {
    override suspend fun getRowData(
        rowData: RowData,
        mediaType: MediaType
    ): Resource<List<ResultItem>> {
        return try {
            val response =
                api.discoverTitles(mediaType = mediaType.value, genreId = rowData.genreId).results
            Resource.Success(data = response, title = rowData.name)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun getTrendingList(mediaType: MediaType): Resource<List<ResultItem>> {
        return try {
            val response =
                api.trendingTitles(mediaType = mediaType.value).results
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun getMovieDetails(id: String, append: String): Resource<Movie> {
        return try {
            val response =
                api.movieDetails(id = id, append = append)
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun getShowDetails(id: String, append: String): Resource<TVShow> {
        return try {
            val response =
                api.showDetails(id = id, append = append)
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override suspend fun getSeasonDetails(id: String, seasonNumber: String): Resource<Season> {
        return try {
            val response =
                api.seasonDetails(id = id, seasonNumber = seasonNumber)
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

    override fun searchTitles(query: String): Flow<PagingData<ResultItem>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchSource(api = api, query = query)
        }.flow

    }


}