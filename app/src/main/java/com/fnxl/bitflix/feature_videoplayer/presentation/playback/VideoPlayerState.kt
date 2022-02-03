package com.fnxl.bitflix.feature_videoplayer.presentation.playback

import com.fnxl.bitflix.core.domain.models.VideoSource
import com.fnxl.bitflix.feature_videoplayer.presentation.components.QuickSeekDirection
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.RendererItem
import org.videolan.libvlc.interfaces.IMedia

data class VideoPlayerState(
    val isSourceChanged: Boolean = true,
    val sourceList: List<VideoSource> = emptyList(),
    val selectedIndex: Int = 0,
    val currentSource: VideoSource = VideoSource(),
    val error: String = "",
    val playing: Boolean = false,
    val buffering: Boolean = true,
    val duration: Long = 0, // in Seconds
    val seeking: Boolean = false,
    val title: String = "",
    val audioTracks: List<MediaPlayer.TrackDescription> = emptyList(),
    val subtitleTracks: List<MediaPlayer.TrackDescription>? = emptyList(),
    val currentAudioTrack: Int = Int.MIN_VALUE,
    val currentSubtitleTrack: Int = Int.MIN_VALUE,
    val renderItems: List<RendererItem> = emptyList(),
    val videoScale: Int = 0,
    val controlsVisible: Boolean = false,
    val quickSeekDirection: QuickSeekDirection = QuickSeekDirection.None,
    // Dropdowns and Dialog States
    val videoTrack: IMedia.VideoTrack? = null,
    val settingsDropDownState: Boolean = false,
    val audioSubtitlesDialog: Boolean = false,
    val castDialog: Boolean = false,
    val informationDialog: Boolean = false,
    val sourceListDialog: Boolean = false,
)