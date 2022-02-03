package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fnxl.bitflix.core.presentation.components.NetworkImage
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.ui.theme.ChipGray
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray

@Composable
fun Backdrop(
    url: String,
    title: String = "Carnage",
    onBackClicked: () -> Unit,
) {
    Box(modifier = Modifier.height(450.dp)) {
        NetworkImage(url = url, contentScale = ContentScale.Crop)

        DetailTopBar(onBackClicked)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            DarkGray
                        ),
                        startY = 400f
                    )
                )
        )

        StandardIconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.Center),
            icon = Icons.Default.PlayArrow,
            contentDescription = "Play",
            backgroundColor = ChipGray,
            alpha = 0.7f
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
    }
}
