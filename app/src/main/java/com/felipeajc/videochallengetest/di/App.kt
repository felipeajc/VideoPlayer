package com.felipeajc.videochallengetest.di

import android.app.Application
import com.felipeajc.videochallengetest.api.ApiModule
import com.felipeajc.videochallengetest.ui.fullscreen.FullscreenModule
import com.felipeajc.videochallengetest.ui.main.MainModule
import com.felipeajc.videochallengetest.ui.main.video.VideoModule

class App : Application() {
    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mainModule(MainModule())
            .videoModule(VideoModule())
            .apiModule(ApiModule())
            .fullscreenModule(FullscreenModule())
            .build()
    }
}
