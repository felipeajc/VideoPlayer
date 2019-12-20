package com.felipeajc.videochallengetest.di

import com.felipeajc.videochallengetest.api.ApiModule
import com.felipeajc.videochallengetest.ui.fullscreen.FullscreenModule
import com.felipeajc.videochallengetest.ui.fullscreen.FullscreenVideoActivity
import com.felipeajc.videochallengetest.ui.main.MainActivity
import com.felipeajc.videochallengetest.ui.main.MainModule
import com.felipeajc.videochallengetest.ui.main.video.VideoFragment
import com.felipeajc.videochallengetest.ui.main.video.VideoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MainModule::class, VideoModule::class, ApiModule::class, FullscreenModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(videoFragment: VideoFragment)

    fun inject(fullScreenActivity: FullscreenVideoActivity)
}