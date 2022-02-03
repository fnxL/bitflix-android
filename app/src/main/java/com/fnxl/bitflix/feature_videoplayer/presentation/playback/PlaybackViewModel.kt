package com.fnxl.bitflix.feature_videoplayer.presentation.playback

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.core.videoplayer.VLCMediaPlayer
import com.fnxl.bitflix.feature_discover.presentation.detail.DetailScreenNavArgs
import com.fnxl.bitflix.feature_videoplayer.presentation.components.QuickSeekDirection
import com.fnxl.bitflix.feature_videoplayer.util.VideoScale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.RendererDiscoverer
import org.videolan.libvlc.RendererItem
import org.videolan.libvlc.interfaces.IMedia
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlaybackViewModel @Inject constructor(
    private val mediaPlayer: VLCMediaPlayer,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var TAG = "Bitflix/Player"
    private val defaultIndex = 0
    private val defaultTimeOut = 5000L
    private val rendererItem = mutableListOf<RendererItem>()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val navArgs = savedStateHandle.get<PlaybackScreenArgs>("args")

    var currentTime by mutableStateOf(0L) // in seconds
        private set

    var state by mutableStateOf(
        VideoPlayerState(
            title = navArgs!!.title,
            sourceList = navArgs.videoSourceList,
            currentSource = navArgs.videoSourceList[defaultIndex]
        )
    )
        private set

    private val handler = Handler(Looper.getMainLooper())
    private val mFadeOut = Runnable { hide() }

    init {
        mediaPlayer.setVideoScale(VideoScale.scale[state.videoScale].first)
        // Set event listeners
        mediaPlayer.setDiscoverEventListener { event ->
            when (event.type) {
                RendererDiscoverer.Event.ItemAdded -> {
                    Timber.d("DNS ${event.item}")
                    rendererItem.add(event.item)
                    state = state.copy(renderItems = rendererItem)
                }
            }
        }

        mediaPlayer.setEventListener { event ->
            when (event.type) {
                MediaPlayer.Event.Opening -> {
                }
                MediaPlayer.Event.EncounteredError -> {
                    // Move to next source item in sourceList
                    state = state.copy(selectedIndex = state.selectedIndex + 1)
                    state = state.copy(
                        currentSource = state.sourceList[state.selectedIndex],
                        isSourceChanged = true
                    )
                    // set Source
                    mediaPlayer.setSource(state.currentSource)
                }
                MediaPlayer.Event.Playing -> {
                    state = state.copy(buffering = false, playing = true)
                }
                MediaPlayer.Event.Paused -> {
                    state = state.copy(playing = false)

                }
                MediaPlayer.Event.Buffering -> {
                    if (event.buffering == 100f) {
                        state = state.copy(buffering = false)
                    } else {
                        state = state.copy(buffering = true)
                    }
                }

                MediaPlayer.Event.TimeChanged -> {
                    if (mediaPlayer.getCurrentTime() > 0 && !state.seeking) {
                        if (currentTime != mediaPlayer.getCurrentTime() / 1000) {
                            currentTime = mediaPlayer.getCurrentTime() / 1000
                        }
                    }
                }
            }
        }
        mediaPlayer.setMediaEventListener {
            when (it.parsedStatus) {
                IMedia.ParsedStatus.Done -> {
                    // Set duration, stats, audio and subtitles tracks and release the media.
                    Timber.d("PLAYER: Parsing Done")
                    // Set OneTime Duration of Media.

                    // Get Video Track Details
                    val videoTrack = mediaPlayer.getVideoTrack()
                    state = state.copy(videoTrack = videoTrack)
                    if (state.duration == 0L) {
                        state = state.copy(duration = mediaPlayer.getDuration() / 1000)
                    }
                    // Get Audio Tracks
                    state = state.copy(
                        audioTracks = mediaPlayer.getAudioTracks(),
                        currentAudioTrack = mediaPlayer.getAudioTrack(),
                    )
                    // Get subtitles track if any
                    if (mediaPlayer.getSubtitleTracks() != null) {
                        state = state.copy(
                            subtitleTracks = mediaPlayer.getSubtitleTracks(),
                            currentSubtitleTrack = mediaPlayer.getSubtitleTrack()
                        )
                    }
                }
            }
        }

        mediaPlayer.setSource(state.currentSource)
        mediaPlayer.startDiscover()
        show()
    }

    fun onEvent(event: MediaEvent) {
        when (event) {

            is MediaEvent.OnDispose -> {
                mediaPlayer.release()
            }
            is MediaEvent.OnAttachView -> {
                mediaPlayer.attachViews(event.view)
            }
            is MediaEvent.OnPlayPauseClick -> {

                if (mediaPlayer.getState() == 3) {
                    show(3600000000)
                } else {
                    show(defaultTimeOut)
                }
                mediaPlayer.togglePlayPause()
            }
            is MediaEvent.OnPlay -> {
                mediaPlayer.play()
                state = state.copy(playing = true)
            }
            is MediaEvent.OnPause -> {
                mediaPlayer.pause()
                state = state.copy(playing = false)
            }
            is MediaEvent.OnSeekStart -> {
                show(3600000000)
                state = state.copy(seeking = true)
                currentTime = event.value.toLong()
            }
            is MediaEvent.OnSeekFinished -> {
                show(defaultTimeOut)
                state = state.copy(seeking = false)
                mediaPlayer.setTime(currentTime * 1000) // in ms
            }
            is MediaEvent.OnForwardClick -> {
                show(defaultTimeOut)
                mediaPlayer.setTime((currentTime + 10) * 1000)
            }
            is MediaEvent.OnRewindClick -> {
                show(defaultTimeOut)
                mediaPlayer.setTime((currentTime - 10) * 1000)
            }
            is MediaEvent.OnCloseClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is MediaEvent.OnAudioTrackChange -> {
                mediaPlayer.setAudioTrack(event.id)
                state = state.copy(currentAudioTrack = event.id)
            }
            is MediaEvent.OnSubtitleTrackChange -> {
                mediaPlayer.setSubtitleTrack(event.id)
                state = state.copy(currentSubtitleTrack = event.id)

            }

            is MediaEvent.OnCastSelect -> {
                mediaPlayer.setRenderer(event.rendererItem)
            }
            is MediaEvent.OnVideoScaleClick -> {
                show(defaultTimeOut)
                val value = (state.videoScale + 1) % VideoScale.scale.size
                state = state.copy(videoScale = value)
                mediaPlayer.setScale(VideoScale.scale[value].first)
                sendUiEvent(UiEvent.Toast(VideoScale.scale[value].second))
            }

            is MediaEvent.OnSourceChange -> {
                state = state.copy(
                    currentSource = event.source,
                    settingsDropDownState = false,
                    sourceListDialog = false,
                    isSourceChanged = true,
                )
                mediaPlayer.setSource(state.currentSource, time = currentTime)
            }
            // Dialog & Drop Downs
            is MediaEvent.OnSettingsClick -> {
                show(3600000000)
                if (state.settingsDropDownState) {
                    state = state.copy(settingsDropDownState = false)
                } else {
                    state = state.copy(settingsDropDownState = true)

                }
            }
            is MediaEvent.OnSettingsDismiss -> {
                show(defaultTimeOut)
                state = state.copy(settingsDropDownState = false)
            }

            is MediaEvent.OnInformationClick -> {
                show(defaultTimeOut)
                state = state.copy(informationDialog = true, settingsDropDownState = false)
            }
            is MediaEvent.OnInformationDialogDismiss -> {
                state = state.copy(informationDialog = false, settingsDropDownState = false)
            }

            is MediaEvent.OnChangeSourceClick -> {
                show(defaultTimeOut)
                state = state.copy(sourceListDialog = true, settingsDropDownState = false)
            }
            is MediaEvent.OnSourceListDismiss -> {
                state = state.copy(sourceListDialog = false, settingsDropDownState = false)
            }

            is MediaEvent.OnCastClick -> {
                show(defaultTimeOut)
                state = state.copy(castDialog = true)
            }
            is MediaEvent.OnCastDialogDispose -> {
                state = state.copy(castDialog = false)
            }

            is MediaEvent.OnAudioSubtitleClick -> {
                show(defaultTimeOut)
                state = state.copy(audioSubtitlesDialog = true)
            }
            is MediaEvent.OnAudioSubtitleDialogDismiss -> {
                state = state.copy(audioSubtitlesDialog = false)
            }

            is MediaEvent.OnTap -> {
                Timber.d("ROUTE: ONTAP CALLED")
                if(state.controlsVisible) {
                    hide()
                } else {
                    if(getMediaState() != 3) show(3600000000)
                    else show(defaultTimeOut)
                }
            }
            is MediaEvent.OnQuickSeekForward -> {
                state = state.copy(quickSeekDirection = QuickSeekDirection.Forward)
                mediaPlayer.setTime((currentTime + 10) * 1000)
            }
            is MediaEvent.OnQuickSeekRewind -> {
                state = state.copy(quickSeekDirection = QuickSeekDirection.Rewind)
                mediaPlayer.setTime((currentTime - 10) * 1000)
            }
            is MediaEvent.OnQuickSeekAnimationEnd -> {
                state = state.copy(quickSeekDirection = QuickSeekDirection.None)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun show(timeout: Long) {
        state = state.copy(controlsVisible = true)
        handler.removeCallbacks(mFadeOut)
        handler.postDelayed(mFadeOut, timeout)
    }

    private fun show() {
        show(defaultTimeOut)
    }

    private fun hide() {
        if (state.controlsVisible)
            state = state.copy(controlsVisible = false)
    }

    private fun getMediaState() = mediaPlayer.getState()

}