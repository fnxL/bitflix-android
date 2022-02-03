package com.fnxl.bitflix.feature_videoplayer.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun PlayerSurface(VLCVideoView: VLCVideoLayout, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { VLCVideoView },
        modifier = modifier.fillMaxSize()
    )
}
