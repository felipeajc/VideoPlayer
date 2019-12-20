package com.felipeajc.videochallengetest.ui.main.video

import com.felipeajc.videochallengetest.api.EndpointApi
import com.felipeajc.videochallengetest.api.apimodel.Video
import io.reactivex.Single


class VideoRepository(private val apiService: EndpointApi) :
    IVideoRepository {
    override fun getShortClips(): Single<Video> {

        return apiService.getVideos()

    }

}