package com.example.rews_v1.ui.logs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rews_v1.R
import com.example.rews_v1.engine.ThreatEngine
import com.example.rews_v1.engine.ThreatEventManager

class LogsFragment : Fragment(R.layout.fragment_logs) {

    private lateinit var adapter: LogsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.logRecyclerView)

        adapter = LogsAdapter(ThreatEngine.getLogs())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        ThreatEventManager.addListener {

            activity?.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}