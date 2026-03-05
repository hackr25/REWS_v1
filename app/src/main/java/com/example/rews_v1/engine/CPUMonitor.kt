package com.example.rews_v1.engine

import android.os.Handler
import android.os.Looper
import java.io.RandomAccessFile

object CPUMonitor {

    fun start(callback: (Int) -> Unit) {

        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {

            override fun run() {

                val cpu = readCPU()

                callback(cpu)

                handler.postDelayed(this, 5000)
            }
        })
    }

    private fun readCPU(): Int {

        return try {

            val reader = RandomAccessFile("/proc/stat", "r")
            val load = reader.readLine()
            reader.close()

            val toks = load.split(" ")

            val idle = toks[4].toLong()
            val cpu = toks[1].toLong() + toks[2].toLong() + toks[3].toLong()

            ((cpu * 100) / (cpu + idle)).toInt()

        } catch (e: Exception) {
            0
        }
    }
}