package com.example.suaranusa.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.adapter.HistoryAdapter
import com.example.suaranusa.R
import com.example.suaranusa.repository.HistoryRepository
import com.example.suaranusa.utils.SessionManager

class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var sm:SessionManager

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(HistoryRepository(requireContext()), requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(context)
        historyAdapter = HistoryAdapter(emptyList())
        recyclerView.adapter = historyAdapter

        viewModel.fetchHistoryItems()
        viewModel.historyItems.observe(viewLifecycleOwner) { historyItems ->
            historyAdapter.updateItems(historyItems)
        }

        return view
    }
}