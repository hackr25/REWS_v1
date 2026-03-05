package com.example.rews_v1.engine

import android.os.FileObserver
import java.io.File

class FileEncryptionMonitor(
    private val path: String,
    private val callback: (String) -> Unit
) : FileObserver(path, CREATE or MODIFY) {

    private val suspiciousExtensions =
        listOf("enc", "locked", "crypt", "crypto", "aes")

    override fun onEvent(event: Int, file: String?) {

        if (file == null) return

        val fullPath = "$path/$file"

        val extension = File(fullPath).extension.lowercase()

        if (extension in suspiciousExtensions) {

            callback("Possible encrypted file detected: $file")
        }
    }
}