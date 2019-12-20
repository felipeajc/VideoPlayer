package com.felipeajc.videochallengetest.ui.main

import com.felipeajc.videochallengetest.ui.base.IBasePresenter
import com.felipeajc.videochallengetest.ui.base.IBaseView

interface MainMVP {

    interface View : IBaseView {
        fun setupViews()
        fun showVideoFragment()
    }

    interface Presenter : IBasePresenter {

    }
}