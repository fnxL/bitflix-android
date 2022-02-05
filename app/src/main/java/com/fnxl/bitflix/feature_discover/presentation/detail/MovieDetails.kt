package com.fnxl.bitflix.feature_discover.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.components.StandardButton
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.Config.BACKDROP_URL
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.dateToYear
import com.fnxl.bitflix.feature_discover.presentation.components.TitleRow
import com.fnxl.bitflix.feature_discover.presentation.detail.components.Backdrop
import com.fnxl.bitflix.feature_discover.presentation.detail.components.GenreSection
import com.fnxl.bitflix.feature_discover.presentation.detail.components.TitleInfoSection
import timber.log.Timber

@Composable
fun MovieDetails(state: DetailState, onEvent: (DetailEvent) -> Unit) {
    val scrollState = rememberScrollState()
    val details = state.movie
    val url = "${BACKDROP_URL}${details.backdrop_path}"
    val mediaType = MediaType.MOVIE


    Column(Modifier.verticalScroll(state = scrollState)) {
        // Backdrop & Title Text
        Backdrop(
            url = url,
            title = details.title,
            onBackClicked = { onEvent(DetailEvent.OnBackClick) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GenreSection(details = details)

            SpacerMedium()

            // Watch Button
            StandardButton(
                text = "WATCH NOW",
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    RoundedCornerMedium
                ),
                onClick = {
                    onEvent(
                        DetailEvent.OnWatchClick(
                            title = details.title,
                            year = dateToYear(details.release_date),
                            type = mediaType
                        )
                    )
                },
                contentPadding = PaddingValues(vertical = 18.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium)
        ) {

            TitleInfoSection(details = details, modifier = Modifier.align(Alignment.Start))

            SpacerMedium()

            // Recommendation
            if (details.recommendations.results.isNotEmpty()) {
                TitleRow(
                    title = "Related Titles",
                    list = details.recommendations.results,
                    onClick = { _, _ -> }
                )
            }
        }
    }
}