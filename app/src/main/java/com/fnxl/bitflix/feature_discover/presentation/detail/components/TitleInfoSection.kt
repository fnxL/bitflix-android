package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.dateToYear
import com.fnxl.bitflix.core.util.getRuntime
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow

@Composable
fun TitleInfoSection(modifier: Modifier = Modifier, details: Movie) {
    Row(modifier = modifier) {
        Text(
            text = dateToYear(details.release_date),
            fontWeight = FontWeight.Medium
        )
        SpacerMedium()
        Text(
            text = getRuntime(details.runtime),
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(SpaceMedium))
    Overview(text = details.overview, maxLine = 5)

}

@Composable
fun TitleInfoSection(modifier: Modifier = Modifier, details: TVShow) {
    Row(modifier = modifier) {
        Text(
            text = dateToYear(details.first_air_date),
            fontWeight = FontWeight.Medium
        )
        SpacerMedium()
        Text(
            text = getRuntime(details.episode_run_time[0]),
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(SpaceMedium))
    Overview(text = details.overview, maxLine = 5)

}