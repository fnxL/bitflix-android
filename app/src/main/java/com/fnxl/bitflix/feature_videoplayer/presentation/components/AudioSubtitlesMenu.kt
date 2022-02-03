package com.fnxl.bitflix.feature_videoplayer.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.fnxl.bitflix.core.presentation.components.StandardAppDialog
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.MediaEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.VideoPlayerState
import org.videolan.libvlc.MediaPlayer

@ExperimentalComposeUiApi
@Composable
fun AudioSubtitlesMenu(
    state: VideoPlayerState,
    onEvent: (MediaEvent) -> Unit
) {
    StandardAppDialog(
        isImmersiveMode = true,
        modifier = Modifier.fillMaxWidth(0.70f),
        dialogState = state.audioSubtitlesDialog,
        onDismissRequest = { onEvent(MediaEvent.OnAudioSubtitleDialogDismiss) }
    ) {
        Row(
            modifier = Modifier
                .padding(SpaceMedium),

            horizontalArrangement = Arrangement.SpaceAround
        ) {
            //Audio Tracks
            Column(Modifier.weight(0.5f)) {
                Text(
                    text = "Audio",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(state.audioTracks) {
                        MenuItem(
                            item = it,
                            selectedItem = state.currentAudioTrack,
                            onClick = {
                                onEvent(MediaEvent.OnAudioTrackChange(it))
                            }
                        )
                    }
                }

            }

            // Subtitle Tracks
            Column(Modifier.weight(0.5f)) {
                Text(
                    text = "Subtitles",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                if (state.subtitleTracks!!.isEmpty()) {
                    Text(text = "No Subtitle Tracks Found!, Click here to fetch it")
                } else {
                    LazyColumn(Modifier.fillMaxWidth()) {
                        items(state.subtitleTracks) {
                            MenuItem(
                                item = it,
                                selectedItem = state.currentSubtitleTrack,
                                onClick = { id ->
                                    onEvent(MediaEvent.OnSubtitleTrackChange(id))
                                })
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MenuItem(item: MediaPlayer.TrackDescription, selectedItem: Int, onClick: (Int) -> Unit) {

    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(item.id) },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = Color.Transparent
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (selectedItem == item.id) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Selected")
            }
            Text(
                text = item.name,
                maxLines = 1,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }

    }
}
