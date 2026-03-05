package com.example.rews_v1.engine

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

object PermissionMonitor {

    @SuppressLint("QueryPermissionsNeeded")
    fun scanInstalledApps(context: Context): Boolean {

        val pm = context.packageManager

        val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

        for (pkg in packages) {

            val perms = pkg.requestedPermissions ?: continue

            val hasOverlay = perms.contains("android.permission.SYSTEM_ALERT_WINDOW")
            val hasInternet = perms.contains("android.permission.INTERNET")

            if (hasOverlay && hasInternet) {
                return true
            }
        }

        return false
    }
}