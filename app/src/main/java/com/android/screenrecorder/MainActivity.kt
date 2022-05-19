package com.android.screenrecorder

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.screenrecorder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
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