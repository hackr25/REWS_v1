package com.example.rews_v1.engine

import android.app.admin.DevicePolicyManager
import android.content.Context

object ScreenLockDetector {

    fun checkDeviceAdmins(context: Context): Boolean {

        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        val admins = dpm.activeAdmins

        return admins != null && admins.isNotEmpty()
    }
}