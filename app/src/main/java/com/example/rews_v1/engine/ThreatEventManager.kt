package com.example.rews_v1.engine

import com.example.rews_v1.model.ThreatLog

object ThreatEventManager {

    private val listeners = mutableListOf<(ThreatLog) -> Unit>()

    fun addListener(listener: (ThreatLog) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (ThreatLog) -> Unit) {
        listeners.remove(listener)
    }

    fun notifyListeners(log: ThreatLog) {
        listeners.forEach { it.invoke(log) }
    }
}