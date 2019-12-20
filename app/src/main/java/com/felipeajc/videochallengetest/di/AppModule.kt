package com.felipeajc.videochallengetest.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Application) {


    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }
}
