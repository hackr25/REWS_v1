package com.example.rews_v1.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.rews_v1.engine.CPUMonitor
import com.example.rews_v1.engine.ThreatEngine

class MonitoringService : Service() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()

        startMonitoringNotification()

        startCPUMonitor()

        startMonitoringLoop()
    }

    private fun startMonitoringNotification() {

        val channelId = "rews_monitor_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "REWS Monitoring",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }

        val notification: Notification =
            NotificationCompat.Builder(this, channelId)
                .setContentTitle("REWS Monitoring Active")
                .setContentText("Monitoring ransomware activity...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()

        startForeground(1, notification)
    }

    private fun startMonitoringLoop() {

        handler.post(object : Runnable {

            override fun run() {

                ThreatEngine.runSecurityScan(applicationContext)

                handler.postDelayed(this, 5000)
            }
        })
    }

    private fun startCPUMonitor() {

        CPUMonitor.start { cpu ->

            if (cpu > 80) {

                ThreatEngine.reportThreat(
                    "HIGH",
                    "High CPU usage detected ($cpu%)",
                    30
                )
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    override fun onDestroy() {

        CPUMonitor.stop()

        handler.removeCallbacksAndMessages(null)

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}