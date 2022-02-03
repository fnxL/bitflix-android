package com.fnxl.bitflix.feature_videoplayer.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.components.StandardAppDialog
import com.fnxl.bitflix.core.presentation.components.StandardTextButton
import com.fnxl.bitflix.core.presentation.ui.theme.RedAccent
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceLarge
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.MediaEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.VideoPlayerState

@ExperimentalComposeUiApi
@Composable
fun DiscoverRendererMenu(
    state: VideoPlayerState,
    onEvent: (MediaEvent) -> Unit
) {

    StandardAppDialog(
        isImmersiveMode = true,
        modifier = Modifier.width(300.dp),
        dialogState = state.castDialog,
        onDismissRequest = { onEvent(MediaEvent.OnCastDialogDispose) }
    ) {
        Column(Modifier.fillMaxWidth()) {
            if (state.renderItems.isEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(SpaceLarge)
                        .fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        color = RedAccent,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(32.dp)
                    )
                    SpacerMedium()
                    Text(text = "Scanning")
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                        start = 16.dp,
                        bottom = 16.dp
                    )
                ) {
                    items(state.renderItems) { item ->
                        StandardTextButton(
                            text = item.displayName,
                            onClick = {
                                onEvent(MediaEvent.OnCastSelect(item))
                            },
                            icon = Icons.Default.Tv
                        )
                    }
                }
            }
        }
    }

}