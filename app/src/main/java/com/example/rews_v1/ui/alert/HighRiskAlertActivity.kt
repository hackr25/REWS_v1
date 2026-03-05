package com.example.rews_v1.ui.alert

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rews_v1.R
import com.example.rews_v1.engine.ThreatEngine

class HighRiskAlertActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_high_risk_alert)

        // Start alarm sound
        mediaPlayer = MediaPlayer.create(
            this,
            android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI
        )
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        val acknowledgeBtn = findViewById<Button>(R.id.acknowledgeBtn)

        acknowledgeBtn.setOnClickListener {

            stopAlarm()

            // Reset detection state
            ThreatEngine.resetRisk()
            ThreatEngine.resetAlert()

            finish()
        }
    }

    private fun stopAlarm() {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
        }

        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        stopAlarm()
        super.onDestroy()
    }
}