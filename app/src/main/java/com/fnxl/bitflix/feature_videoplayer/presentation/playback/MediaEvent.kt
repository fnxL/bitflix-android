package com.fnxl.bitflix.feature_videoplayer.presentation.playback

import com.fnxl.bitflix.core.domain.models.VideoSource
import org.videolan.libvlc.Media
import org.videolan.libvlc.RendererItem
import org.videolan.libvlc.util.VLCVideoLayout

sealed class MediaEvent {
    object OnAudioSubtitleDialogDismiss : MediaEvent()
    object OnDispose : MediaEvent()
    data class OnAttachView(val view: VLCVideoLayout) : MediaEvent()
    object OnPlayPauseClick : MediaEvent()
    data class OnSeekStart(val value: Float) : MediaEvent()
    data class OnAudioTrackChange(val id: Int) : MediaEvent()
    data class OnSubtitleTrackChange(val id: Int) : MediaEvent()
    object OnSeekFinished : MediaEvent()
    object OnAudioSubtitleClick : MediaEvent()
    object OnForwardClick : MediaEvent()
    object OnRewindClick : MediaEvent()
    object OnCloseClick : MediaEvent()
    object OnCastDialogDispose : MediaEvent()
    data class OnCastSelect(val rendererItem: RendererItem) : MediaEvent()
    data class OnSourceChange(val source: VideoSource) : MediaEvent()

    object OnTap : MediaEvent()
    object OnQuickSeekForward: MediaEvent()
    object OnQuickSeekAnimationEnd : MediaEvent()
    object OnQuickSeekRewind: MediaEvent()
    object OnPlay : MediaEvent()
    object OnPause : MediaEvent()
    object OnCastClick : MediaEvent()
    object OnVideoScaleClick : MediaEvent()
    object OnSettingsDismiss : MediaEvent()
    object OnInformationDialogDismiss : MediaEvent()
    object OnInformationClick : MediaEvent()
    object OnChangeSourceClick : MediaEvent()
    object OnSettingsClick : MediaEvent()
    object OnSourceListDismiss : MediaEvent()

}
