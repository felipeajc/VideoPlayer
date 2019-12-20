package com.felipeajc.videochallengetest.ui.main.video

import com.felipeajc.videochallengetest.api.apimodel.Video
import com.felipeajc.videochallengetest.ui.base.IBasePresenter
import com.felipeajc.videochallengetest.ui.base.IBaseView
import io.reactivex.Single

interface VideoMVP {

    interface Model {
        fun getData(): Single<Video>
    }

    interface View : IBaseView {
        fun showNetworkError()

        fun showData(videos:Video)

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter : IBasePresenter {
        fun getPageData()
    }

}