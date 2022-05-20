package com.android.screenrecorder.system.logger.di

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
    fun providesTimberTree(): RollingFileTimberTree = RollingFileTimberTree()
}