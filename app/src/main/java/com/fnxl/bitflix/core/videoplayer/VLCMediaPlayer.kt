package com.fnxl.bitflix.core.videoplayer

import android.net.Uri
import com.fnxl.bitflix.core.domain.models.VideoSource
import org.videolan.libvlc.*
import org.videolan.libvlc.interfaces.IMedia
import org.videolan.libvlc.util.VLCVideoLayout
import timber.log.Timber

class VLCMediaPlayer(private val libVLC: LibVLC) {

    private val TAG = "VLCMediaPlayer"

    private lateinit var media: Media

    private lateinit var mediaListener: (IMedia.Event) -> Unit

    private val mediaPlayer = MediaPlayer(libVLC)

    private val renderDiscover = RendererDiscoverer(libVLC, "microdns_renderer")

    fun startDiscover() {
        renderDiscover.start()
        val list = RendererDiscoverer.list(libVLC)
        list.forEach {
            Timber.d("dns ${it.name}")
        }
    }

    fun setDiscoverEventListener(listener: (RendererDiscoverer.Event) -> Unit) {
        renderDiscover.setEventListener {
            listener(it)
        }
    }

    fun setMediaEventListener(listener: (IMedia.Event) -> Unit) {
        mediaListener = listener
    }

    fun attachViews(view: VLCVideoLayout) {
        mediaPlayer.attachViews(view, null, true, false)
    }

    fun play() {
        mediaPlayer.play()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun setScale(scale: MediaPlayer.ScaleType) {
        mediaPlayer.videoScale = scale
    }

    fun setRenderer(item: RendererItem) {
        mediaPlayer.setRenderer(item)
    }

    fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.play()
        }
    }

    fun setSource(source: VideoSource, time: Long? = null) {
        media = Media(
            libVLC,
            Uri.parse(source.url)
        ).apply {
            setHWDecoderEnabled(true, true)
            setEventListener(mediaListener)
        }
        mediaPlayer.media = media
        if (time is Long) media.addOption(":start-time=$time")
        play()
    }

    fun setVideoScale(scale: MediaPlayer.ScaleType) {
        mediaPlayer.videoScale = scale
    }

    fun setEventListener(listener: (MediaPlayer.Event) -> Unit) {
        mediaPlayer.setEventListener {
            listener(it)
        }
    }

    fun getDuration(): Long = mediaPlayer.length

    fun release() {
        renderDiscover.release()
        libVLC.release()
        mediaPlayer.release()
    }

    fun releaseMedia() {
        media.release()
    }

    fun getCurrentTime(): Long = mediaPlayer.time

    fun setTime(time: Long) {
        mediaPlayer.setTime(time, true)
    }

    fun getAudioTracks(): List<MediaPlayer.TrackDescription> = mediaPlayer.audioTracks.toList()

    fun getSubtitleTracks(): List<MediaPlayer.TrackDescription>? = mediaPlayer.spuTracks?.toList()

    fun getVideoTrack(): IMedia.VideoTrack = mediaPlayer.currentVideoTrack

    fun getAudioTrack(): Int = mediaPlayer.audioTrack

    fun getSubtitleTrack(): Int = mediaPlayer.spuTrack

    fun setAudioTrack(id: Int) = mediaPlayer.setAudioTrack(id)

    fun setSubtitleTrack(id: Int) = mediaPlayer.setSpuTrack(id)


    fun getState(): Int =
        mediaPlayer.playerState

    fun stopDiscover() {
        renderDiscover.stop()
    }

}