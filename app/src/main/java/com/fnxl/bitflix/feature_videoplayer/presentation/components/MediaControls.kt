package com.fnxl.bitflix.feature_videoplayer.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.text.format.DateUtils
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.components.StandardProgressIndicator
import com.fnxl.bitflix.core.presentation.ui.theme.RedPrimary
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.Constants.OVERLAY_ALPHA
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.MediaEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.VideoPlayerState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MediaControls(state: VideoPlayerState, onEvent: (MediaEvent) -> Unit, currentTime: Long) {
    val density = LocalDensity.current


    // Media Controls
    Box(
        modifier = Modifier
            .fillMaxSize()
            .overlay(state.controlsVisible)
            .bufferIndicator(state)
    ) {
        //Tap Gesture
        OnTapGestures(onEvent = onEvent)
        // Top Controls
        AnimatedVisibility(
            visible = state.controlsVisible,
            enter = slideInVertically {
                with(density) {
                    -40.dp.roundToPx()
                }
            },
            exit = slideOutVertically() + shrinkVertically(),
            modifier = Modifier.align(Alignment.TopCenter)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMedium, vertical = SpaceSmall),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StandardIconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = { onEvent(MediaEvent.OnCloseClick) },
                        icon = Icons.Default.Close,
                        contentDescription = "Close",
                        alpha = 0f,
                    )
                    Text(text = state.title)
                }
                // Right Section
                Row() {
                    StandardIconButton(
                        modifier = Modifier.size(60.dp),
                        icon = Icons.Default.Cast,
                        contentDescription = "Cast",
                        alpha = 0f,
                        onClick = { onEvent(MediaEvent.OnCastClick) }
                    )

                    StandardIconButton(
                        modifier = Modifier.size(60.dp),
                        icon = Icons.Default.Subtitles,
                        contentDescription = "Aduio/Subtitles",
                        alpha = 0f,
                        onClick = { onEvent(MediaEvent.OnAudioSubtitleClick) }
                    )

                    StandardIconButton(
                        modifier = Modifier.size(60.dp),
                        icon = Icons.Default.AspectRatio,
                        contentDescription = "Change Aspect Ratio",
                        alpha = 0f,
                        onClick = { onEvent(MediaEvent.OnVideoScaleClick) }
                    )

                    SettingsDropDown(state = state, onEvent = onEvent, anchor = {
                        StandardIconButton(
                            modifier = Modifier.size(60.dp),
                            icon = Icons.Default.Settings,
                            contentDescription = "Settings",
                            alpha = 0f,
                            onClick = { onEvent(MediaEvent.OnSettingsClick) })
                    })

                }
            }

        }


        // Center Controls
        AnimatedVisibility(
            visible = state.controlsVisible,
            enter = fadeIn(initialAlpha = 0.7f),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {

                StandardIconButton(
                    iconModifier = Modifier.size(48.dp),
                    onClick = { onEvent(MediaEvent.OnRewindClick) },
                    icon = Icons.Default.Replay10,
                    alpha = 0f,
                    contentDescription = "Rewind 10 seconds"
                )

                Spacer(modifier = Modifier.size(48.dp))
                if (state.buffering) {
                    StandardProgressIndicator(modifier = Modifier.size(85.dp))
                } else {
                    StandardIconButton(
                        iconModifier = Modifier.size(85.dp),
                        alpha = 0f,
                        onClick = { onEvent(MediaEvent.OnPlayPauseClick) },
                        icon = if (state.playing) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause"
                    )
                }
                Spacer(modifier = Modifier.size(48.dp))
                StandardIconButton(
                    iconModifier = Modifier.size(48.dp),
                    alpha = 0f,
                    onClick = { onEvent(MediaEvent.OnForwardClick) },
                    icon = Icons.Default.Forward10,
                    contentDescription = "Forward 10 seconds"
                )

            }
        }
        // Bottom Controls
        AnimatedVisibility(
            visible = state.controlsVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMedium, vertical = SpaceSmall)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-12).dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = DateUtils.formatElapsedTime(currentTime))
                    Text(text = DateUtils.formatElapsedTime(state.duration - currentTime))
                }

                Slider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = currentTime.toFloat(),
                    onValueChange = { value -> onEvent(MediaEvent.OnSeekStart(value)) },
                    valueRange = 0F..state.duration.toFloat(),
                    onValueChangeFinished = { onEvent(MediaEvent.OnSeekFinished) },
                    colors = SliderDefaults.colors(
                        thumbColor = RedPrimary,
                        activeTrackColor = RedPrimary,
                        inactiveTrackColor = Color.LightGray
                    )
                )

            }
        }

    }
}

@Composable
fun OnTapGestures(onEvent: (MediaEvent) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures {
                onEvent(MediaEvent.OnTap)
            }
        })
}

suspend fun PointerInputScope.detectTapGestures(
    onTap: (Offset) -> Unit,
) {
    coroutineScope {

        launch {
            detectTapGestures(
                onTap = onTap
            )
        }
    }
}

fun Modifier.bufferIndicator(state: VideoPlayerState) = composed {
    if (!state.controlsVisible && state.buffering) {
        StandardProgressIndicator(modifier = Modifier.fillMaxSize())
    }
    this
}

fun Modifier.overlay(controlsVisible: Boolean) = composed {
    if (controlsVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent.copy(Constants.OVERLAY_ALPHA),
                        0.2f to Color.Transparent,
                    )
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent.copy(Constants.OVERLAY_ALPHA)
                        ),
                        startY = 270f

                    )
                )
        )
    }
    this
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    heightDp = 360,
    widthDp = 720,
    backgroundColor = android.graphics.Color.WHITE.toLong()
)
@Composable
fun MediaControlsPreview() {
    MediaControls(
        onEvent = {},
        state = VideoPlayerState(title = "Pilot", controlsVisible = true),
        currentTime = 0
    )
}