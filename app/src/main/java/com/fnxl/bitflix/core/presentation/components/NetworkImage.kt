package com.fnxl.bitflix.core.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.ui.theme.ShimmerDarkGray
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    circularRevealEnabled: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    bitmapPalette: BitmapPalette? = null,
) {

    CoilImage(
        imageModel = url,
        modifier = modifier,
        contentScale = contentScale,
        circularReveal = if (circularRevealEnabled) CircularReveal(duration = 250) else null,
        bitmapPalette = bitmapPalette,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = ShimmerDarkGray,
            dropOff = 0.65f
        ),
        error = ImageBitmap.imageResource(id = R.drawable.ic_poster_placeholder)
    )
}