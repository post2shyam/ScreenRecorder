package com.android.screenrecorder.system.logger

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import timber.log.Timber
import java.io.Closeable
import java.io.File
import java.io.OutputStreamWriter

class RollingFileTimberTree(val application: Application) : Timber.Tree(), Engine, Closeable {
    private var engineStarted: Boolean = false
    private val baseFileName = "logs"
    private var fileCount = 0
    private var file: File
    private var outputStreamWriter: OutputStreamWriter
    private val maxFileSize = 1

    init {
        file = File(application.filesDir, fileName())
        outputStreamWriter = OutputStreamWriter(file.outputStream())
    }

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //If logging engine is started
        if (engineStarted) {
            when (priority) {
                Log.VERBOSE -> {
                    Log.v(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()

                }
                Log.DEBUG -> {
                    Log.d(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.INFO -> {
                    Log.i(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.WARN -> {
                    Log.w(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                Log.ERROR -> {
                    Log.e(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
                else -> {
                    //Log.ASSERT
                    Log.wtf(tag, message)
                    checkAndSplitLogfile(message.length)
                    outputStreamWriter.write(message)
                    outputStreamWriter.flush()
                }
            }
        }
    }

    private fun checkAndSplitLogfile(length: Int) {
        if (file.length() + length > maxFileSize) {
            //Close the current file handle
            outputStreamWriter.close()

            //Update file count
            fileCount++

            //Create new file handle
            file = File(application.filesDir, fileName())
            outputStreamWriter = OutputStreamWriter(file.outputStream())
        }
    }

    private fun fileName() = "$baseFileName$fileCount"

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