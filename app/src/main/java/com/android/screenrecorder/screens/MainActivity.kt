package com.android.screenrecorder.screens

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.screenrecorder.R
import com.android.screenrecorder.databinding.ActivityMainBinding
import com.android.screenrecorder.system.logger.RollingFileTimberTree
import dagger.hilt.android.AndroidEntryPoint
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

        setupUi()
        rollingFileTimberTree.start()
        Timber.tag("SSG").d("onCreate:")
        rollingFileTimberTree.stop()
        Timber.tag("SSG2").d("onCreate:")
    }

    private fun setupUi() {
        with(binding) {
            playStopButton.setOnClickListener {
                val button = it as Button

                //Toggle current text of the button
                button.text = if (button.text.equals(getString(R.string.play))) {
                    getString(R.string.stop)
                } else {
                    getString(R.string.play)
                }
            }
        }
    }
}