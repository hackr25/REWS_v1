package com.example.rews_v1.engine

import android.os.Handler
import android.os.HandlerThread
import java.io.RandomAccessFile

object CPUMonitor {

    private var handlerThread: HandlerThread? = null
    private var handler: Handler? = null

    private var prevIdle: Long = 0
    private var prevTotal: Long = 0

    fun start(callback: (Int) -> Unit) {

        handlerThread = HandlerThread("CPU_MONITOR_THREAD")
        handlerThread?.start()

        handler = Handler(handlerThread!!.looper)

        handler?.post(object : Runnable {

            override fun run() {

                val cpuUsage = calculateCPU()

                callback(cpuUsage)

                handler?.postDelayed(this, 5000)
            }
        })
    }

    fun stop() {

        handler?.removeCallbacksAndMessages(null)

        handlerThread?.quitSafely()

        handlerThread = null
        handler = null
    }

    private fun calculateCPU(): Int {

        return try {

            val reader = RandomAccessFile("/proc/stat", "r")

            val load = reader.readLine()

            reader.close()

            val toks = load.split("\\s+".toRegex())

            val user = toks[1].toLong()
            val nice = toks[2].toLong()
            val system = toks[3].toLong()
            val idle = toks[4].toLong()

            val total = user + nice + system + idle

            if (prevTotal == 0L) {

                prevTotal = total
                prevIdle = idle

                return 0
            }

            val totalDiff = total - prevTotal
            val idleDiff = idle - prevIdle

            prevTotal = total
            prevIdle = idle

            if (totalDiff == 0L) 0
            else ((totalDiff - idleDiff) * 100 / totalDiff).toInt()

        } catch (e: Exception) {

            0
        }
    }
}