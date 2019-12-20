package com.felipeajc.videochallengetest.ui.main.video

import com.felipeajc.videochallengetest.api.ApiModule
import com.felipeajc.videochallengetest.api.EndpointApi
import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class])
class VideoModule {

    @Provides
    fun provideVideoPresenter(model: VideoMVP.Model): VideoMVP.Presenter {
        return VideoPresenter(model)
    }

    @Provides
    fun provideVideoModel(repository: IVideoRepository): VideoMVP.Model {
        return VideoModel(repository)
    }

    @Provides
    fun provideVideoRepository(apiService: EndpointApi): IVideoRepository {
        return VideoRepository(apiService)
    }

}