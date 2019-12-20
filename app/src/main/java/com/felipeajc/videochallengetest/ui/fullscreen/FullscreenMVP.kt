package com.felipeajc.videochallengetest.ui.fullscreen

import com.felipeajc.videochallengetest.ui.base.IBasePresenter
import com.felipeajc.videochallengetest.ui.base.IBaseView

interface FullscreenMVP {

    interface Model

    interface View : IBaseView {
        fun closeFullscreenActivity()

        fun makeActivityFullscreen()

        fun delayedHide()

        fun hide()

        fun changeFullscreenButtonToExit()

        fun setupPlayer()
    }

    interface Presenter: IBasePresenter {
        fun handleFullscreenButtonClick()
        fun setNavigatiingToFullscreenFalse()


    }

}