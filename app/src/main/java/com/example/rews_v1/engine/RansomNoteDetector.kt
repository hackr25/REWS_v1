package com.example.rews_v1.engine

object RansomNoteDetector {

    private val ransomPatterns = listOf(
        "decrypt",
        "recover",
        "restore",
        "readme",
        "how_to"
    )

    fun isRansomNote(filename: String): Boolean {

        val lower = filename.lowercase()

        return ransomPatterns.any { lower.contains(it) }
    }
}