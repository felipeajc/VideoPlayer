package com.felipeajc.videochallengetest.api

import com.felipeajc.videochallengetest.api.apimodel.Video
import io.reactivex.Single
import retrofit2.http.GET


interface EndpointApi {

    @GET("videos.json")
    fun getVideos(): Single<Video>

}