package com.example.rews_v1.engine

import android.os.FileObserver
import java.io.File

class FileEncryptionMonitor(
    private val path: String,
    private val callback: (String) -> Unit
) : FileObserver(path, CREATE or MODIFY) {

    override fun onEvent(event: Int, file: String?) {

        if (file == null) return

        val extension = File(file).extension.lowercase()

        if (extension in listOf("enc","locked","crypt","crypto","aes")) {

            callback("Encrypted file detected: $file")
        }
    }
}