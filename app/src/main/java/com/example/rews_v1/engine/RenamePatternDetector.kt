package com.example.rews_v1.engine

import android.os.FileObserver
import android.os.SystemClock

class RenamePatternDetector(
    private val path: String,
    private val callback: (String) -> Unit
) : FileObserver(path, MOVED_TO) {

    private var renameCount = 0
    private var startTime = SystemClock.elapsedRealtime()

    override fun onEvent(event: Int, file: String?) {

        val now = SystemClock.elapsedRealtime()

        if (now - startTime > 5000) {
            renameCount = 0
            startTime = now
        }

        renameCount++

        if (renameCount > 10) {

            callback("Mass file rename pattern detected")

            renameCount = 0
        }
    }
}