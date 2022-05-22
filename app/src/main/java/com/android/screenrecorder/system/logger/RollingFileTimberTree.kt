package com.android.screenrecorder.system.logger

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import timber.log.Timber
import java.io.Closeable
import java.io.File
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class RollingFileTimberTree(val application: Application) : Timber.Tree(), Engine, Closeable {

    private var engineStarted: Boolean = false
    private val baseFileName = "logs"
    private var fileCount = 0
    private var file: File
    private var outputStreamWriter: OutputStreamWriter
    private val maxFileSizeInMBytes = 3
    private lateinit var listener: Listener

    init {
        file = File(application.filesDir, fileName())
        outputStreamWriter = OutputStreamWriter(file.outputStream())
    }

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //If logging engine is started
        if (engineStarted) {
            checkAndSplitLogfile(message.toByteArray(Charset.defaultCharset()).size)
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

    fun attachListener(listener: Listener) {
        this.listener = listener
    }

    fun fileName() = "$baseFileName$fileCount"

    private fun checkAndSplitLogfile(length: Int) {
        if (file.length() + length > (maxFileSizeInMBytes * bytesPerMegaByte)) {
            //Close the current file handle
            outputStreamWriter.close()

            //Update file count
            fileCount++

            //Create new file handle
            val fileName = fileName()
            file = File(application.filesDir, fileName)
            outputStreamWriter = OutputStreamWriter(file.outputStream())

            //Notify Logfile Rollover has happened
            listener.newFileRollOver()
        }
    }

    companion object {
        const val bytesPerMegaByte = 10//1_000_000
    }

    interface Listener {
        fun newFileRollOver()
    }
}