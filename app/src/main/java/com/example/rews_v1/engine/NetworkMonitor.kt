package com.example.rews_v1.engine

import android.net.TrafficStats

object NetworkMonitor {

    private var lastBytes = TrafficStats.getTotalRxBytes()

    fun checkNetworkUsage(): Long {

        val current = TrafficStats.getTotalRxBytes()

        val diff = current - lastBytes

        lastBytes = current

        return diff
    }
}