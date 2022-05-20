package com.android.screenrecorder.screens

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.android.screenrecorder.R
import com.android.screenrecorder.databinding.ActivityMainBinding
import com.android.screenrecorder.system.logger.RollingFileTimberTree
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rollingFileTimberTree: RollingFileTimberTree

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rollingFileTimberTree.start()

        setupUi()
        startDummyLogging()
    }

    private fun startDummyLogging() {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            var i = 0
            while (isActive) {
                delay(2000)
                Timber.tag("SSG").d("${i++}")
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            playStopButton.setOnClickListener {

                val button = it as Button

                //Toggle current text of the button
                button.text = if (button.text.equals(getString(R.string.play))) {
                    rollingFileTimberTree.stop()
                    getString(R.string.stop)
                } else {
                    rollingFileTimberTree.start()
                    getString(R.string.play)
                }
            }
        }
    }
}