package com.felipeajc.videochallengetest.player

import android.content.Context
import android.net.Uri
import android.view.SurfaceView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.*


class ExoPlayerViewManager private constructor(
    private val context: Context,
    private var exoPlayerView: PlayerView?,
    private val url: String,
    private val audioUrl: String
) {

    init {
        instances[url] = this
    }

    private var player: SimpleExoPlayer? = null

    fun playVideo(listener: Player.EventListener?) {

        if (exoPlayerView == null) {
            return
        }


        releaseVideoPlayer()

        if (player == null) {
            initializePlayer()

            val userAgent = Util.getUserAgent(context, "Video Challenge")

            val mediaSourceVideo = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                    .setExtractorsFactory(DefaultExtractorsFactory())
                    .createMediaSource(Uri.parse(url))

            val mediaSourceAudio = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                .setExtractorsFactory(DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(audioUrl))


            if (listener != null)
                player!!.addListener(listener)

            player!!.prepare(LoopingMediaSource(MergingMediaSource(mediaSourceVideo, mediaSourceAudio)))

            //player!!.prepare(mediaSource)
            player!!.playWhenReady = true
        }

        setPlayerProperties()
    }

    fun setPlayerProperties() {
        if (player != null) {

            player!!.clearVideoSurface()
            player!!.setVideoSurfaceView(exoPlayerView!!.videoSurfaceView as SurfaceView)
            player!!.seekTo(player!!.currentPosition + 1)
            exoPlayerView!!.player = player
        }
    }

    fun releaseVideoPlayer() {
        if (player != null) {
            player!!.release()
        }
        player = null
    }

    fun isPlaying(): Boolean {
        return player?.playbackState == Player.STATE_READY && player?.playWhenReady!!
    }

    fun pausePlayer() {
        player?.playWhenReady = false
        player?.playbackState
    }


    private fun initializePlayer() {

        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(context)

        //How set the URL audio on Video

        player = ExoPlayerFactory.newSimpleInstance(
            context,
            renderersFactory,
            trackSelector,
            loadControl
        )

    }

    companion object {

        const val EXTRA_VIDEO_URI = "video_uri"
        const val EXTRA_AUDIO_URI = "audio_uri"
        val instances = HashMap<String, ExoPlayerViewManager>()

        fun getInstance(
            context: Context,
            exoPlayerView: PlayerView?,
            url: String?,
            audioUrl: String?
        ): ExoPlayerViewManager {

            return if (instances[url] == null) {
                ExoPlayerViewManager(context, exoPlayerView, url!!, audioUrl!!)
            } else {
                val instance = instances[url]!!
                instance.exoPlayerView = exoPlayerView
                instance.setPlayerProperties()
                instance
            }

        }

        fun getInstance(url: String): ExoPlayerViewManager? {
            return instances[url]
        }
    }
}