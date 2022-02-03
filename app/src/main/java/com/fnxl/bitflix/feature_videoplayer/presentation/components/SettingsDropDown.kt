package com.fnxl.bitflix.feature_videoplayer.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fnxl.bitflix.core.domain.models.VideoSource
import com.fnxl.bitflix.core.presentation.components.*
import com.fnxl.bitflix.core.presentation.ui.theme.MediumGray
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.MediaEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.VideoPlayerState

@Composable
fun SettingsDropDown(
    state: VideoPlayerState,
    onEvent: (MediaEvent) -> Unit,
    anchor: @Composable () -> Unit,
) {

    StandardDropDownMenu(
        isImmersive = true,
        modifier = Modifier.fillMaxWidth(0.3f),
        expanded = state.settingsDropDownState,
        onDismissRequest = { onEvent(MediaEvent.OnSettingsDismiss) },
        anchor = anchor
    ) {

        DropdownMenuItem(onClick = { onEvent(MediaEvent.OnInformationClick) }) {
            Text(
                text = "Information",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        DropdownMenuItem(onClick = { onEvent(MediaEvent.OnChangeSourceClick) }) {
            Text(
                text = "Change Source",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun SourceListMenuItem(
    source: VideoSource,
    currentSource: VideoSource,
    maxLine: Int = 1,
    onClick: (VideoSource) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(SpaceMedium)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (source == currentSource) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green
                )
            }
            SpacerSmall()
            Text(
                text = source.name,
                maxLines = if (expanded) Int.MAX_VALUE else maxLine
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(Modifier.padding(SpaceSmall)) {
                SpacerSmall()
                Row {
                    Text(text = "Size: ", fontWeight = FontWeight.Bold)
                    Text(text = source.size)
                }
                SpacerSmall()
                StandardButton(text = "Set Source") {
                    onClick(source)
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun SourceListMenu(state: VideoPlayerState, onEvent: (MediaEvent) -> Unit) {
    StandardAppDialog(
        isImmersiveMode = true,
        modifier = Modifier.fillMaxWidth(0.70f),
        dialogState = state.sourceListDialog,
        onDismissRequest = { onEvent(MediaEvent.OnSourceListDismiss) }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(SpaceMedium)
        ) {
            Text(text = "Sources:", fontWeight = FontWeight.Bold)
            LazyColumn() {
                items(state.sourceList) {
                    SourceListMenuItem(
                        source = it,
                        currentSource = state.currentSource,
                        onClick = { source ->
                            onEvent(MediaEvent.OnSourceChange(source))
                        }
                    )
                }
            }

        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun InformationDialog(state: VideoPlayerState, onEvent: (MediaEvent) -> Unit) {
    val videoTrack = state.videoTrack
    StandardAppDialog(
        isImmersiveMode = true,
        modifier = Modifier.fillMaxWidth(0.6f),
        dialogState = state.informationDialog,
        onDismissRequest = { onEvent(MediaEvent.OnInformationDialogDismiss) }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(SpaceMedium)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "File Name:", fontWeight = FontWeight.Bold)
            Text(text = state.currentSource.name, maxLines = 5)

            SpacerSmall()

            Text(text = "Size:", fontWeight = FontWeight.Bold)
            Text(
                text = state.currentSource.size.replace(oldValue = "+", newValue = " "),
                maxLines = 5
            )

            SpacerSmall()

            Text(text = "Codec:", fontWeight = FontWeight.Bold)
            videoTrack?.codec?.let { Text(text = it) }

            SpacerSmall()

            Text(text = "Original Codec:", fontWeight = FontWeight.Bold)
            videoTrack?.originalCodec?.let { Text(text = it) }

            SpacerSmall()

            Text(text = "Resolution:", fontWeight = FontWeight.Bold)
            Text(text = "${videoTrack?.width} x ${videoTrack?.height}")

            SpacerSmall()

            Text(text = "Framerate:", fontWeight = FontWeight.Bold)
            Text(text = "${videoTrack?.frameRateDen?.let { videoTrack.frameRateNum.div(it) }} fps")

        }
    }
}
