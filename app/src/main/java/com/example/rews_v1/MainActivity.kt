package com.example.rews_v1

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rews_v1.service.MonitoringService
import com.example.rews_v1.ui.dashboard.DashboardFragment
import com.example.rews_v1.ui.logs.LogsFragment
import com.example.rews_v1.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        requestAppPermissions()

        loadFragment(DashboardFragment())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        startMonitoringService()

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> loadFragment(DashboardFragment())
                R.id.nav_logs -> loadFragment(LogsFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
            }
            true
        }
    }

    private fun requestAppPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }
    }

    private fun startMonitoringService() {

        val intent = Intent(this, MonitoringService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}