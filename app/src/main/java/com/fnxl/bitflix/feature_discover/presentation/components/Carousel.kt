package com.fnxl.bitflix.feature_discover.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.SpacerSmall
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.ui.theme.ShimmerDarkGray
import com.fnxl.bitflix.core.util.Config
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.google.accompanist.pager.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@Composable
fun Carousel(list: List<ResultItem>, onClick: (Int) -> Unit) {

    val pagerState = rememberPagerState()

    Column {
        HorizontalPager(
            count = list.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth()
                .aspectRatio(1.2f),
        ) { page ->

            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }

                    .clip(RoundedCornerShape(40.dp))

            ) {
                val item = list[page]
                CarouselItem(item, onClick)
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = Color.DarkGray,
            indicatorWidth = 7.dp,
        )
    }


}

@ExperimentalPagerApi
@Composable
fun CarouselItem(item: ResultItem, onClick: (Int) -> Unit) {

    Box(
        Modifier.clickable { onClick(item.id) }
    ) {
        CoilImage(
            imageModel = Config.POSTER_URL + item.poster_path,
            contentDescription = item.title,
            contentScale = ContentScale.FillBounds,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f,
                highlightColor = ShimmerDarkGray
            ),

            )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF111112)
                        ),
                        startY = 700f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StandardIconButton(
                    icon = Icons.Default.PlayArrow,
                    contentDescription = stringResource(id = R.string.play),
                    onClick = {},
                )
                SpacerSmall()
                Text(text = "Play Now", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }

        }


    }


}

