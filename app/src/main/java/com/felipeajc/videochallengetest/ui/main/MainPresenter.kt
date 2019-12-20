package com.felipeajc.videochallengetest.ui.main

import com.felipeajc.videochallengetest.ui.base.IBaseView

class MainPresenter : MainMVP.Presenter {


    lateinit var mView: MainMVP.View

    private fun getMvpView(): MainMVP.View {
        return mView
    }

    override fun setView(view: IBaseView) {
        mView = view as MainMVP.View

        mView.setupViews()
    }
}