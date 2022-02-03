package com.fnxl.bitflix.feature_discover.presentation.detail

import com.fnxl.bitflix.core.domain.models.VideoSource
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Season

data class DetailState(
    val title: String = "",
    val movie: Movie = Movie(),
    val tvShow: TVShow = TVShow(),
    val seasonDetails: Season = Season(),
    val filteredSeasonList: List<com.fnxl.bitflix.feature_discover.domain.model.tvshow.Season> = listOf(),
    val loading: Boolean = true,
    val error: String = "",
    val selectedSeasonIndex: Int = 0,
    val seasonDropDown: Boolean = false,
    val linksDialog: Boolean = false,
    val linksDialogLoading: Boolean = true,
    val streamLinks: Map<String, List<VideoSource>?> = emptyMap()
)
