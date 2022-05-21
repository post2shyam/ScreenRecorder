package com.android.screenrecorder.system.logger.di

import android.app.Application
import com.android.screenrecorder.system.logger.RollingFileTimberTree
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
    @Singleton
    @Provides
    fun providesTimberTree(application: Application): RollingFileTimberTree {
       return  RollingFileTimberTree(application)
    }
}