package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.NetworkImage
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMediumSmall
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.Config.BACKDROP_URL
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Episode

@Composable
fun EpisodeList(
    list: List<Episode>,
    onClick: (Int) -> Unit,
) {
    list.forEach {
        EpisodeListItem(item = it, onClick = onClick)
    }
}

@Composable
fun EpisodeListItem(item: Episode, onClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(vertical = SpaceMedium)) {
        // Image, title & runtime.
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .clip(RoundedCornerShape(RoundedCornerMediumSmall))
            ) {
                NetworkImage(
                    url = BACKDROP_URL + item.still_path,
                    modifier = Modifier.aspectRatio(16 / 9f),
                    contentScale = ContentScale.FillWidth
                )
                StandardIconButton(
                    onClick = { onClick(item.episode_number) },
                    modifier = Modifier.align(Alignment.Center),
                    alpha = 0.5f,
                    icon = Icons.Default.PlayArrow,
                    contentDescription = "Play"
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "${item.episode_number} - ${item.name}", textAlign = TextAlign.Start)
        }
        Spacer(modifier = Modifier.size(6.dp))
        Overview(text = item.overview, maxLine = 2)
    }
}