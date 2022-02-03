package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun GenreSection(details: Movie) {
    FlowRow(
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 8.dp
    ) {
        if (details.genres.size > 3) {
            details.genres.slice(0..2).forEach {
                GenreChip(name = it.name)
            }
        } else {
            details.genres.forEach {
                GenreChip(name = it.name)
            }
        }
    }
}

@Composable
fun GenreSection(details: TVShow) {
    FlowRow(
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 8.dp
    ) {
        if (details.genres.size > 3) {
            details.genres.slice(0..2).forEach {
                GenreChip(name = it.name)
            }
        } else {
            details.genres.forEach {
                GenreChip(name = it.name)
            }
        }
    }
}