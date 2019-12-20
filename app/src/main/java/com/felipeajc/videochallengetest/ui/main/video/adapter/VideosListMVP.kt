package com.felipeajc.videochallengetest.ui.main.video.adapter

import com.felipeajc.videochallengetest.api.apimodel.VideoDataModel
import com.felipeajc.videochallengetest.api.apimodel.Video

interface VideosListMVP {

    interface Model

    interface View {
        fun setThumbnailImage(url: String, title: String)

        fun setVideoTitle(title: String)

        fun showVideoAndHideThumbnail()

        fun showThumbnailAndHideVideo()

        fun showFullScreenActivity(url: String, audio: String)

        fun makeVideoPlayerInstance(videoDataModel: VideoDataModel)

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter {

        fun onBindVideoRow(holder: VideosListAdapter.VideosViewHolder, position: Int)

        fun getItemCount(): Int

        fun deactivate()

        fun play(holder: VideosListAdapter.VideosViewHolder, videoDataModel: VideoDataModel, position: Int)

        fun releaseAllPlayers()

        fun pauseAllPlayers()

        fun handleItemViewClick(holder: VideosListAdapter.VideosViewHolder, position: Int)

        fun handlePlayerFullScreenClick(holder: VideosListAdapter.VideosViewHolder, position: Int)

        fun setAdapterData(videos: Video?)

        fun resumeCurrentVideoOnBackFromFullScreen()

        fun setClip(videoDataModelModel: VideoDataModel)

        fun setHolder(holder: VideosListAdapter.VideosViewHolder)

        fun setCurrentHolderAndClip(videoDataModelModel: VideoDataModel, holder: VideosListAdapter.VideosViewHolder)

        fun getClipModelByPosition(position: Int): VideoDataModel

        fun getVideoUrlByPosition(position: Int): String?

        fun pauseOutOfScreenVideo(position: Int)

        fun handlePauseCondition(position: Int)

        fun showThumb(position: Int)
    }
}