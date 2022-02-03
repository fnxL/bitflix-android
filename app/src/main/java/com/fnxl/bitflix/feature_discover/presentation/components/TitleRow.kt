package com.fnxl.bitflix.feature_discover.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.NetworkImage
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMediumSmall
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import timber.log.Timber


@Composable
fun TitleRow(
    modifier: Modifier = Modifier,
    title: String,
    list: List<ResultItem>,
    onClick: (Int, MediaType?) -> Unit,
) {

    Column(modifier = modifier) {
        RowTitle(title = title, onClick = {})
        LazyRow {
            itemsIndexed(list) { index, item ->
                TitleRowPoster(
                    modifier = Modifier.padding(
                        end = if (index == list.size - 1) 0.dp else SpaceSmall,
                        top = 8.dp
                    ),
                    item = item,
                    onClick = { id, media ->
                        onClick(id, media)
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleRowPreview() {
    TitleRow(
        title = "Popular Movies",
        list = listOf(ResultItem(poster_path = "/b6qUu00iIIkXX13szFy7d0CyNcg.jpg")),
        onClick = { it, string -> })
}

@Composable
fun TitleRowPoster(
    modifier: Modifier = Modifier,
    item: ResultItem?,
    progress: Float? = null,
    onClick: (Int, MediaType?) -> Unit
) {

    Box(
        modifier = modifier
            .width(120.dp)
            .clip(RoundedCornerShape(RoundedCornerMediumSmall))
            .clickable {
                val mediaType =
                    if (item?.media_type == "movie") MediaType.MOVIE else MediaType.TVShow
                Timber.d("ROUTE MEDIATYPE ${mediaType}")
                if (item != null) {
                    onClick(item.id, mediaType)
                }
            }
    ) {

        if (item != null) {
            NetworkImage(
                url = Config.POSTER_URL + item.poster_path,
                modifier = Modifier.aspectRatio(0.73f),
                contentScale = ContentScale.FillWidth
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(0.2f),
                            Color(0xFF111112)
                        ),
                        startY = 200f
                    )
                )
        )

        progress?.let {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(4.dp)
                    .align(Alignment.BottomStart),
                color = MaterialTheme.colors.primary
            ) {

            }
        }

    }

}
