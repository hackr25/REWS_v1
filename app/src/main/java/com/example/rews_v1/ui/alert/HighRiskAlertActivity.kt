package com.example.rews_v1.ui.alert

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rews_v1.R

class HighRiskAlertActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_high_risk_alert)

        mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer?.start()

        val acknowledgeBtn = findViewById<Button>(R.id.acknowledgeBtn)

        acknowledgeBtn.setOnClickListener {
            mediaPlayer?.stop()
            finish()
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}