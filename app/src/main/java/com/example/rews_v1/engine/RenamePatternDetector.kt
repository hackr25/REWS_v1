package com.example.rews_v1.engine

import android.os.FileObserver

class RenamePatternDetector(
    private val path: String,
    private val callback: (String) -> Unit
) : FileObserver(path, MOVED_TO) {

    private var renameCount = 0

    override fun onEvent(event: Int, file: String?) {

        renameCount++

        if (renameCount > 10) {

            callback("Mass file rename pattern detected")

            renameCount = 0
        }
    }
}