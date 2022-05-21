package com.android.screenrecorder.system.logger

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import timber.log.Timber
import java.io.Closeable
import java.io.OutputStreamWriter

class RollingFileTimberTree(application: Application) : Timber.Tree(), Engine, Closeable {

    private var engineStarted: Boolean = false

    private var fileName = "logs"

    private val outputStreamWriter: OutputStreamWriter

    init {
        outputStreamWriter =
            OutputStreamWriter(application.openFileOutput(fileName, Context.MODE_PRIVATE))
    }

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //If logging engine is started
        if (engineStarted) {
            when (priority) {
                Log.VERBOSE -> {
                    Log.v(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.DEBUG -> {
                    Log.d(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.INFO -> {
                    Log.i(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.WARN -> {
                    Log.w(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.ERROR -> {
                    Log.e(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                else -> {
                    //Log.ASSERT
                    Log.wtf(tag, message)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
            }
        }
    }

    override fun start() {
        engineStarted = true
    }

    override fun stop() {
        engineStarted = false
    }

    override fun close() {
        outputStreamWriter.close()
    }
}