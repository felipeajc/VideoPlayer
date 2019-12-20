package com.felipeajc.videochallengetest.ui.fullscreen

import com.felipeajc.videochallengetest.constants.Constants
import com.felipeajc.videochallengetest.ui.base.IBaseView

class FullscreenPresenter : FullscreenMVP.Presenter {


    lateinit var mView: FullscreenMVP.View

    private fun getMvpView(): FullscreenMVP.View {
        return mView
    }

    override fun setNavigatiingToFullscreenFalse() {
        Constants.isNavigatingToFullScreen = false
    }

    override fun handleFullscreenButtonClick() {
        getMvpView().closeFullscreenActivity()
    }

    override fun setView(view: IBaseView) {
        mView = view as FullscreenMVP.View
    }


}