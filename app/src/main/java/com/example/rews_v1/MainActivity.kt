package com.example.rews_v1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rews_v1.service.MonitoringService
import com.example.rews_v1.ui.dashboard.DashboardFragment
import com.example.rews_v1.ui.logs.LogsFragment
import com.example.rews_v1.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_main)

        loadFragment(DashboardFragment())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val intent = Intent(this, MonitoringService::class.java)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dashboard -> loadFragment(DashboardFragment())
                R.id.nav_logs -> loadFragment(LogsFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}