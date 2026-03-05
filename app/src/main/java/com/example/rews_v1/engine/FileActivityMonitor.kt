package com.example.rews_v1.engine

object FileActivityMonitor {

    private var fileEvents = 0
    private var lastCheck = System.currentTimeMillis()

    fun registerEvent(): Boolean {

        fileEvents++

        val now = System.currentTimeMillis()

        if (now - lastCheck > 10000) {

            val burst = fileEvents

            fileEvents = 0
            lastCheck = now

            if (burst > 200) {
                return true
            }
        }

        return false
    }
}