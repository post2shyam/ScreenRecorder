package com.android.screenrecorder.system.logger

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

class RollingFileTimberTree : Timber.Tree(), Engine {

    private var engineStarted: Boolean = false

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //If logging engine is started
        if (engineStarted) {
            when (priority) {
                Log.VERBOSE -> Log.v(tag, message)
                Log.DEBUG -> Log.d(tag, message)
                Log.INFO -> Log.i(tag, message)
                Log.WARN -> Log.w(tag, message)
                Log.ERROR -> Log.e(tag, message)
                else -> Log.wtf(tag, message) //Log.ASSERT
            }
        }
    }

    override fun start() {
        engineStarted = true
    }

    override fun stop() {
        engineStarted = false
    }
}