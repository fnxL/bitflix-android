package com.fnxl.bitflix.core.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import com.fnxl.bitflix.destinations.MovieScreenDestination
import com.fnxl.bitflix.destinations.TVShowScreenDestination
import com.google.accompanist.pager.ExperimentalPagerApi


sealed class NavigationItem(
    var route: String,
    var icon: ImageVector,
    var title: String,
    val contentDescription: String
) {
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    object Movies : NavigationItem(
        route = MovieScreenDestination.route,
        icon = Icons.Default.Movie,
        title = "Movies",
        contentDescription = "Movies"
    )
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    object TVShows : NavigationItem(
        route = TVShowScreenDestination.route,
        icon = Icons.Default.Tv,
        title = "TV Shows",
        contentDescription = "TV Shows"
    )
}