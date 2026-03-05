package com.example.rews_v1.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rews_v1.R
import com.example.rews_v1.engine.ThreatEngine
import com.example.rews_v1.engine.ThreatEventManager

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scanButton = view.findViewById<Button>(R.id.scanButton)
        val scanProgress = view.findViewById<ProgressBar>(R.id.scanProgress)
        val scanStatusText = view.findViewById<TextView>(R.id.scanStatusText)

        scanButton.setOnClickListener {

            scanProgress.visibility = View.VISIBLE
            scanStatusText.text = "Scanning..."
            scanButton.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({

                ThreatEngine.runSecurityScan(requireContext())

                val risk = ThreatEngine.getRiskScore()
                val level = ThreatEngine.getRiskLevel()

                scanProgress.visibility = View.GONE
                scanStatusText.text = "Scan Complete"

                val riskProgress = view.findViewById<ProgressBar>(R.id.riskProgress)
                val riskText = view.findViewById<TextView>(R.id.riskText)

                riskProgress.progress = risk
                riskText.text = level

                scanButton.isEnabled = true

            }, 3000)
        }

        ThreatEventManager.addListener {

            activity?.runOnUiThread {

                val riskProgress = view.findViewById<ProgressBar>(R.id.riskProgress)
                val riskText = view.findViewById<TextView>(R.id.riskText)

                riskProgress.progress = ThreatEngine.getRiskScore()
                riskText.text = ThreatEngine.getRiskLevel()

                ThreatEngine.checkHighRisk(requireContext())
            }
        }
    }
}