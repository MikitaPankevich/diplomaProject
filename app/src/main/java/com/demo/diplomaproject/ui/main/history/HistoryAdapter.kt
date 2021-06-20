package com.demo.diplomaproject.ui.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.R
import com.demo.diplomaproject.domain.entity.TestResult

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    private val history = mutableListOf<TestResult>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(history[position])
    }

    override fun getItemCount() = history.size

    fun showHistory(history: List<TestResult>) {
        this.history.clear()
        this.history.addAll(history)
        notifyDataSetChanged()
    }
}