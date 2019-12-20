package com.felipeajc.videochallengetest.ui.main.video

import com.felipeajc.videochallengetest.api.apimodel.Video
import io.reactivex.Single

interface IVideoRepository {
    fun getShortClips(): Single<Video>
}