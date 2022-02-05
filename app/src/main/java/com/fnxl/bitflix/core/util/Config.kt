package com.fnxl.bitflix.core.util

import androidx.compose.ui.ExperimentalComposeUiApi
import com.fnxl.bitflix.core.domain.models.NavigationItem
import com.fnxl.bitflix.core.domain.models.RowData
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_ACTION
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_ACTION_ADVENTURE
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_CRIME
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_HORROR
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_SCIFI
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_SCIFI_FANTASY
import com.fnxl.bitflix.core.util.Genre.GENRE_ID_THRILLER
import com.google.accompanist.pager.ExperimentalPagerApi

object Config {

    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val WATCH_REGION_DEFAULT = "IN"
    const val POSTER_URL = "https://image.tmdb.org/t/p/w500"
    const val BACKDROP_URL = "https://image.tmdb.org/t/p/w1280"

//    const val SERVER_BASE_URL = "https://api-bitflix.herokuapp.com/api/"
    const val SERVER_BASE_URL = "http://localhost:5000/api/"

    const val APPEND_TO_RESPONSE_MOVIE =
        "videos,releases,recommendations,credits,external_ids,images"
    const val APPEND_TO_RESPONSE_TV = "videos,content_ratings,recommendations,credits,external_ids"

    val MovieRowData = listOf(
        RowData("Popular Movies"),
        RowData("Action", GENRE_ID_ACTION),
        RowData("Crime", GENRE_ID_CRIME),
        RowData("SciFi", GENRE_ID_SCIFI),
        RowData("Thriller", GENRE_ID_THRILLER),
        RowData("Horror", GENRE_ID_HORROR),
    )

    val TVShowRowData = listOf(
        RowData("Popular Shows"),
        RowData("Action & Adventure", GENRE_ID_ACTION_ADVENTURE),
        RowData("SciFi & Fantasy", GENRE_ID_SCIFI_FANTASY),
    )

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    val bottomNavItems = listOf(
        NavigationItem.Movies,
        NavigationItem.TVShows
    )
}