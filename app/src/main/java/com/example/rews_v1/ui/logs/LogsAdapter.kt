package com.example.rews_v1.ui.logs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rews_v1.R
import com.example.rews_v1.model.ThreatLog

class LogsAdapter(private val logs: List<ThreatLog>) :
    RecyclerView.Adapter<LogsAdapter.LogViewHolder>() {

    class LogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val riskLevel: TextView = view.findViewById(R.id.logRiskLevel)
        val message: TextView = view.findViewById(R.id.logMessage)
        val timestamp: TextView = view.findViewById(R.id.logTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logs[position]

        holder.riskLevel.text = log.riskLevel
        holder.message.text = log.message
        holder.timestamp.text = log.timestamp

        // Color coding
        when (log.riskLevel) {
            "HIGH" -> holder.riskLevel.setTextColor(0xFFFF5252.toInt())
            "MEDIUM" -> holder.riskLevel.setTextColor(0xFFFFC107.toInt())
            else -> holder.riskLevel.setTextColor(0xFF00E676.toInt())
        }
    }

    override fun getItemCount(): Int = logs.size
}