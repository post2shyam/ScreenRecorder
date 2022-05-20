package com.android.screenrecorder.system.application

import android.app.Application
import com.android.screenrecorder.system.logger.RollingFileTimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var rollingFileTimberTree: RollingFileTimberTree

    override fun onCreate() {
        super.onCreate()
        enableLogger()
    }

    private fun enableLogger() = Timber.plant(rollingFileTimberTree)
}