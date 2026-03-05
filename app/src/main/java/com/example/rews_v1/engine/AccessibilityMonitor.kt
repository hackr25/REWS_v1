package com.example.rews_v1.engine

import android.content.Context
import android.provider.Settings

object AccessibilityMonitor {

    fun isAccessibilityActive(context: Context): Boolean {

        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )

        return !enabledServices.isNullOrEmpty()
    }
}