package com.felipeajc.videochallengetest.ui.main.video

import com.felipeajc.videochallengetest.api.apimodel.Video
import io.reactivex.Single

class VideoModel(val repository: IVideoRepository) :
    VideoMVP.Model {
    override fun getData(): Single<Video> {
        return repository.getShortClips()
    }

}