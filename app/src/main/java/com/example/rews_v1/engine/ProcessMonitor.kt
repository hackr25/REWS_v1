package com.example.rews_v1.engine

import android.app.ActivityManager
import android.content.Context

object ProcessMonitor {

    fun checkProcesses(context: Context): Int {

        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val processes = manager.runningAppProcesses

        return processes?.size ?: 0
    }
}