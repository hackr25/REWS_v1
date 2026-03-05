package com.example.rews_v1.engine

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.example.rews_v1.model.ThreatLog
import com.example.rews_v1.ui.alert.HighRiskAlertActivity

object ThreatEngine {

    private var riskScore = 0
    private val logs = mutableListOf<ThreatLog>()

    // Main scan function
    fun runSecurityScan(context: Context) {

        checkOverlayAbuse(context)

        checkAccessibilityServices(context)

        checkHighRisk(context)
    }

    // Central function to report threats
    fun reportThreat(level: String, message: String, score: Int) {

        val log = createLog(level, message, score)

        logs.add(0, log)

        ThreatEventManager.notifyListeners(log)
    }

    // Overlay abuse detection
    fun checkOverlayAbuse(context: Context) {

        if (Settings.canDrawOverlays(context)) {

            reportThreat(
                "MEDIUM",
                "Screen overlay permission detected",
                20
            )
        }
    }

    // Accessibility abuse detection
    fun checkAccessibilityServices(context: Context) {

        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )

        if (!enabledServices.isNullOrEmpty()) {

            reportThreat(
                "MEDIUM",
                "Accessibility service active on device",
                15
            )
        }
    }

    // High risk alert trigger
    fun checkHighRisk(context: Context) {

        if (riskScore >= 70) {

            val intent = Intent(context, HighRiskAlertActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }
    }

    // Log creator
    private fun createLog(level: String, message: String, scoreIncrease: Int): ThreatLog {

        riskScore += scoreIncrease

        if (riskScore > 100) riskScore = 100

        return ThreatLog(
            riskLevel = level,
            message = message,
            timestamp = java.text.SimpleDateFormat("hh:mm a")
                .format(java.util.Date())
        )
    }

    fun getRiskScore(): Int = riskScore

    fun getRiskLevel(): String {

        return when {

            riskScore < 30 -> "LOW"

            riskScore < 70 -> "MEDIUM"

            else -> "HIGH"
        }
    }

    fun getLogs(): List<ThreatLog> = logs

    fun resetRisk() {
        riskScore = 0
    }
}