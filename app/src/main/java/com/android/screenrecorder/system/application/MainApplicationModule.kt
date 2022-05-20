package com.android.screenrecorder.system.application

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainApplicationModule {
    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext appContext: Context): MainApplication =
        appContext as MainApplication
}