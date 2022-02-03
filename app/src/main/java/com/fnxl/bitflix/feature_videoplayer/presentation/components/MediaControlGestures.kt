package com.fnxl.bitflix.feature_videoplayer.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import com.fnxl.bitflix.core.presentation.components.ShadowedIconButton
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.MediaEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.VideoPlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MediaControlGestures(
    modifier: Modifier = Modifier,
    state: VideoPlayerState,
    onEvent: (MediaEvent) -> Unit
) {
    val controlsVisible = state.controlsVisible
    val quickSeekDirection = state.quickSeekDirection

    if (!controlsVisible) {
        Box(modifier = modifier.quickSeekAnimation(quickSeekDirection) {
            onEvent(MediaEvent.OnQuickSeekAnimationEnd)
        }) {
            // TODO: Drag gestures 
            Gestures(onEvent = onEvent)
        }
    }
}

@Composable
fun Gestures(onEvent: (MediaEvent) -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectMediaPlayerGesture(
                onTap = {
                    onEvent(MediaEvent.OnTap)
                },
                onDoubleTap = { offset ->
                    when {
                        offset.x < size.width * 0.4f -> {
                            onEvent(MediaEvent.OnQuickSeekRewind)
                        }
                        offset.x > size.width * 0.6f -> {
                            onEvent(MediaEvent.OnQuickSeekForward)
                        }
                    }
                },
            )
        })
}

suspend fun PointerInputScope.detectMediaPlayerGesture(
    onTap: (Offset) -> Unit,
    onDoubleTap: (Offset) -> Unit,
) {
    coroutineScope {

        launch {
            detectTapGestures(
                onTap = onTap,
                onDoubleTap = onDoubleTap
            )
        }
    }
}

fun Modifier.quickSeekAnimation(quickSeekDirection: QuickSeekDirection ,onAnimationEnd: () -> Unit) = composed {
    val alphaRewind = remember { Animatable(0f) }
    val alphaForward = remember { Animatable(0f) }

    LaunchedEffect(quickSeekDirection) {
        when (quickSeekDirection) {
            QuickSeekDirection.Rewind -> alphaRewind
            QuickSeekDirection.Forward -> alphaForward
            else -> null
        }?.let { animatable ->
            animatable.animateTo(1f)
            animatable.animateTo(0f)
            onAnimationEnd()
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ShadowedIconButton(
                Icons.Filled.FastRewind,
                modifier = Modifier
                    .alpha(alphaRewind.value)
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ShadowedIconButton(
                Icons.Filled.FastForward,
                modifier = Modifier
                    .alpha(alphaForward.value)
                    .align(Alignment.Center)
            )
        }
    }

    this
}

enum class QuickSeekDirection {
    None,
    Rewind,
    Forward
}