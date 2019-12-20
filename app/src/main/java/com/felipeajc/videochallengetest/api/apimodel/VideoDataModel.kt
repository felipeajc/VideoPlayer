package com.felipeajc.videochallengetest.api.apimodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class VideoDataModel {

    @SerializedName("video_url")
    @Expose
    var video_url: String? = null

    @SerializedName("thumb")
    @Expose
    var thumb: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("audio_urls")
    @Expose
    var audioUrlsList: AudioUrls? = null

    var showVideoAndHideThumbnail: Boolean = false
    var showLoading: Boolean = false

    var audioSet: String? = null
}

data class AudioUrls(
    @SerializedName("de") val de: String? = null,
    @SerializedName("en") val en: String? = null,
    @SerializedName("cn") val cn: String? = null,
    @SerializedName("zh") val zh: String? = null
)
