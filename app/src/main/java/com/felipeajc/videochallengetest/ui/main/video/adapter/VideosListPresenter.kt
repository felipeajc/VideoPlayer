package com.felipeajc.videochallengetest.ui.main.video.adapter

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.felipeajc.videochallengetest.api.apimodel.VideoDataModel
import com.felipeajc.videochallengetest.api.apimodel.Video
import com.felipeajc.videochallengetest.player.ExoPlayerViewManager


class VideosListPresenter(private var videos: Video?) :
    VideosListMVP.Presenter {

    private lateinit var currentVideoDataModel: VideoDataModel
    private lateinit var currentHolder: VideosListAdapter.VideosViewHolder
    private lateinit var oldHolder: VideosListAdapter.VideosViewHolder
    private var currentPosition = 0
    private var currentLastPosition = -1


    override fun handleItemViewClick(holder: VideosListAdapter.VideosViewHolder, position: Int) {
        val url = getVideoUrlByPosition(position)
        val clip = getClipModelByPosition(position)

        if (url != null) {
            holder.makeVideoPlayerInstance(clip)
            setCurrentHolderAndClip(clip, holder)
            play(holder, clip, position)
        }
    }

    override fun handlePlayerFullScreenClick(
        holder: VideosListAdapter.VideosViewHolder,
        position: Int
    ) {
        val url = getVideoUrlByPosition(position)
        val video = getClipModelByPosition(position)
        currentPosition = position

        if (url != null) {

            setCurrentHolderAndClip(video, holder)
            holder.showFullScreenActivity(video.video_url!!, video.audioSet!!)
        }
    }

    override fun deactivate() {
    }

    override fun play(holder: VideosListAdapter.VideosViewHolder, videoDataModel: VideoDataModel, position: Int) {

        currentPosition = position

        if(currentLastPosition > -1)
        {
            handlePauseCondition(currentLastPosition)
            oldHolder.showThumbnailAndHideVideo()
        }

        oldHolder = holder
        currentLastPosition = currentPosition

        val instance = ExoPlayerViewManager.getInstance(videoDataModel.video_url!!)
        instance?.playVideo((object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    ExoPlayer.STATE_READY -> {
                        videoDataModel.showLoading = false
                        videoDataModel.showVideoAndHideThumbnail = true
                    }

                    ExoPlayer.STATE_ENDED -> {
                        videoDataModel.showVideoAndHideThumbnail = false
                    }

                    ExoPlayer.STATE_BUFFERING -> {
                        videoDataModel.showLoading = true
                    }
                }

                onBindVideoRow(holder, position)
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                videoDataModel.showLoading = false
            }
        }))

    }

    override fun resumeCurrentVideoOnBackFromFullScreen() {
        if (::currentHolder.isInitialized && ::currentVideoDataModel.isInitialized)
            currentHolder.makeVideoPlayerInstance(currentVideoDataModel)
    }

    override fun releaseAllPlayers() {
        val instances = ExoPlayerViewManager.instances
        for (url in instances.keys)
            instances[url]?.releaseVideoPlayer()
    }

    override fun pauseAllPlayers() {
        val allInstances = ExoPlayerViewManager.instances
        for (url in allInstances.keys)
            allInstances[url]?.pausePlayer()
    }


    override fun onBindVideoRow(holder: VideosListAdapter.VideosViewHolder, position: Int) {
        val videoModel = getClipModelByPosition(position)

        holder.setThumbnailImage(videoModel.thumb!!, videoModel.title!!)
        holder.setVideoTitle(videoModel.title!!)


        if (videoModel.showVideoAndHideThumbnail)
            holder.showVideoAndHideThumbnail()
        else
            holder.showThumbnailAndHideVideo()


        if (videoModel.showLoading)
            holder.showLoading()
        else
            holder.hideLoading()
    }

    override fun getItemCount(): Int {
        return videos?.videoDataModels!!.size
    }

    override fun setAdapterData(videos: Video?) {
        this.videos = videos!!
    }

    override fun setClip(videoDataModelModel: VideoDataModel) {
        currentVideoDataModel = videoDataModelModel
    }

    override fun setHolder(holder: VideosListAdapter.VideosViewHolder) {
        currentHolder = holder
    }

    override fun setCurrentHolderAndClip(
        videoDataModelModel: VideoDataModel,
        holder: VideosListAdapter.VideosViewHolder
    ) {
        setClip(videoDataModelModel)
        setHolder(holder)
    }

    override fun getClipModelByPosition(position: Int): VideoDataModel {
        return videos?.videoDataModels?.get(position)!!
    }

    override fun getVideoUrlByPosition(position: Int): String? {
        return getClipModelByPosition(position).video_url
    }

    override fun showThumb(position: Int) {

    }

    override fun pauseOutOfScreenVideo(position: Int) {
        val url = getVideoUrlByPosition(position)
        if (url != null) {
            val currentInstance = ExoPlayerViewManager.getInstance(url)
            if (currentInstance != null)
                if (currentInstance.isPlaying())
                    currentInstance.pausePlayer()
        }
    }

    override fun handlePauseCondition(
        lastVideoPosition: Int
    ) {

        getClipModelByPosition(lastVideoPosition).showVideoAndHideThumbnail = false
        pauseOutOfScreenVideo(lastVideoPosition)
    }
}
