package com.fnxl.bitflix.feature_discover.data.remote

import com.fnxl.bitflix.BuildConfig
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultResponse
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Season
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface TmdbApi {

    @GET("discover/{media_type}?api_key=${BuildConfig.TMDB_API_KEY}&sort_by=popularity.desc&include_adult=true&&with_watch_monetization_types=flatrate|free|ads|rent|buy")
    suspend fun discoverTitles(
        @Path("media_type") mediaType: String,
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: Int? = null,
        @QueryMap options: Map<String, String> = mapOf("watch_region" to Config.WATCH_REGION_DEFAULT)
    ): ResultResponse

    @GET("trending/{media_type}/{time_window}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun trendingTitles(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String = "day",
    ): ResultResponse

    @GET("search/multi?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun searchItem(
        @Query("query") query: String?,
        @Query("page") page: Int = 1,
        @QueryMap filter: Map<String, String> = emptyMap()
    ): ResultResponse

    @GET("movie/{id}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun movieDetails(
        @Path("id") id: String,
        @Query("append_to_response") append: String
    ): Movie

    @GET("tv/{id}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun showDetails(
        @Path("id") id: String,
        @Query("append_to_response") append: String
    ): TVShow

    @GET("tv/{id}/season/{season_number}?api_key=${BuildConfig.TMDB_API_KEY}")
    suspend fun seasonDetails(
        @Path("id") id: String,
        @Path("season_number") seasonNumber: String,
    ): Season

}