package com.fnxl.bitflix.feature_discover.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.components.StandardButton
import com.fnxl.bitflix.core.presentation.components.StandardDropDownMenu
import com.fnxl.bitflix.core.presentation.components.StandardDropDownMenuItem
import com.fnxl.bitflix.core.presentation.ui.theme.MediumGray
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerSmall
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.dateToYear
import com.fnxl.bitflix.feature_discover.presentation.components.TitleRow
import com.fnxl.bitflix.feature_discover.presentation.detail.components.Backdrop
import com.fnxl.bitflix.feature_discover.presentation.detail.components.EpisodeList
import com.fnxl.bitflix.feature_discover.presentation.detail.components.GenreSection
import com.fnxl.bitflix.feature_discover.presentation.detail.components.TitleInfoSection

@Composable
fun TVShowDetails(state: DetailState, onEvent: (DetailEvent) -> Unit) {
    val scrollState = rememberScrollState()
    val details = state.tvShow
    val url = "${Config.BACKDROP_URL}${details.backdrop_path}"
    val filteredSeasonList = state.filteredSeasonList
    val mediaType = MediaType.TVShow

    Column(Modifier.verticalScroll(state = scrollState)) {
        // Backdrop & Title Text
        Backdrop(
            url = url,
            title = details.name,
            onBackClicked = { onEvent(DetailEvent.OnBackClick) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                            title = details.name,
                            year = dateToYear(details.first_air_date),
                            type = mediaType,
                            episodeNumber = 1,
                            seasonNumber = 1,
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

            // Season Dropdown
            StandardDropDownMenu(
                expanded = state.seasonDropDown,
                onDismissRequest = { onEvent(DetailEvent.OnSeasonDropDownDismiss) },
                anchor = {
                    StandardButton(
                        text = "Season ${filteredSeasonList[state.selectedSeasonIndex].season_number}",
                        shape = RoundedCornerShape(
                            RoundedCornerSmall
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MediumGray,
                            contentColor = LocalContentColor.current
                        ),
                        onClick = { onEvent(DetailEvent.OnSeasonDropDownClick) }
                    )
                }
            ) {
                filteredSeasonList.forEachIndexed { index, season ->
                    StandardDropDownMenuItem(
                        onClick = {
                            onEvent(
                                DetailEvent.OnSeasonChange(
                                    index,
                                    details.id.toString()
                                )
                            )
                        },
                        text = "Season ${season.season_number}"
                    )
                }
            }
            SpacerMedium()
            EpisodeList(
                list = state.seasonDetails.episodes,
                onClick = { episodeNumber ->
                    onEvent(
                        DetailEvent.OnWatchClick(
                            title = details.name,
                            year = dateToYear(details.first_air_date),
                            seasonNumber = filteredSeasonList[state.selectedSeasonIndex].season_number,
                            episodeNumber = episodeNumber,
                            type = mediaType,
                        )
                    )
                }
            )

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
