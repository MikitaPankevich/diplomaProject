package com.demo.diplomaproject.ui.main.settings.pacients.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.R
import com.demo.diplomaproject.domain.entity.TestResult
import java.util.ArrayList

class PatientsHistoryAdapter : RecyclerView.Adapter<PatientsHistoryViewHolder>() {

    private val patientHistory = mutableListOf<TestResult>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientsHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_patient_history, parent, false)
        return PatientsHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientsHistoryViewHolder, position: Int) {
        holder.bind(patientHistory[position])
    }

    override fun getItemCount() = patientHistory.size

    fun showPatientHistory(patientHistory: List<TestResult>) {
        this.patientHistory.clear()
        this.patientHistory.addAll(patientHistory)
        notifyDataSetChanged()
    }
}