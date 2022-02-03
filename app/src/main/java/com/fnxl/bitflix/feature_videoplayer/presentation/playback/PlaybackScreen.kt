package com.fnxl.bitflix.feature_videoplayer.presentation.playback

import android.content.pm.ActivityInfo
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.LockScreenOrientation
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.feature_videoplayer.presentation.components.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect
import org.videolan.libvlc.util.VLCVideoLayout

@ExperimentalComposeUiApi
@Composable
@Destination(navArgsDelegate = PlaybackScreenNavArgs::class)
fun PlaybackScreen(
    viewModel: PlaybackViewModel = hiltViewModel(),
    navArgsDelegate: PlaybackScreenNavArgs,
    navigator: DestinationsNavigator
) {

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)

    val snackBar = LocalSnackbar.current
    val state = viewModel.state

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var previousToast by remember {
        mutableStateOf<Toast?>(null)
    }
    // Video Player View & Controls
    val videoView = VLCVideoLayout(LocalContext.current)
    val systemUi = rememberSystemUiController()


    DisposableEffect(lifecycleOwner) {
        systemUi.setSystemBarsColor(Color.Transparent)
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {

                }
                Lifecycle.Event.ON_DESTROY -> {
                    // Release libVLC and media player instance from memory on activity destroyed.
                    viewModel.onEvent(MediaEvent.OnDispose)
                }
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.onEvent(MediaEvent.OnPause)
                }
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.onEvent(MediaEvent.OnAttachView(videoView))
                    viewModel.onEvent(MediaEvent.OnPlay)
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            systemUi.setSystemBarsColor(DarkGray)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.SnackBar -> {
                    snackBar.showSnackbar(event.text)
                }
                is UiEvent.PopBackStack -> {
                    navigator.popBackStack()
                }
                is UiEvent.Toast -> {
                    previousToast?.cancel()
                    previousToast = Toast.makeText(context, event.text, Toast.LENGTH_SHORT)
                    previousToast?.show()
                }
                else -> Unit
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        PlayerSurface(VLCVideoView = videoView)

        MediaControls(
            onEvent = viewModel::onEvent,
            state = state,
            currentTime = viewModel.currentTime
        )

        MediaControlGestures(state = state, onEvent = viewModel::onEvent)

        // TODO: Allow background playback when casting and media controls

        // Audio & Subtitles Dialog
        AudioSubtitlesMenu(state = state, onEvent = viewModel::onEvent)
        InformationDialog(state = viewModel.state, onEvent = viewModel::onEvent)
        SourceListMenu(state = viewModel.state, onEvent = viewModel::onEvent)
        DiscoverRendererMenu(state = viewModel.state, onEvent = viewModel::onEvent)
    }

}

