package com.felipeajc.videochallengetest.api.apimodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Video {

    @SerializedName("videos")
    @Expose
    var videoDataModels: List<VideoDataModel>? = null
}
