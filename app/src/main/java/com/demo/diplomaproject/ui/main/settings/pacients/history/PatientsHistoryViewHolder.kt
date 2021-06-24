package com.demo.diplomaproject.ui.main.settings.pacients.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.domain.entity.TestResult
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_patient_history.*
import java.text.SimpleDateFormat
import java.util.*

class PatientsHistoryViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView = itemView

    fun bind(patientHistory: TestResult) {
        patientAverageRms.text = patientHistory.averageRms
        patientMinRms.text = patientHistory.minRms
        patientMaxRms.text = patientHistory.maxRms
        patientResult.text = patientHistory.qualityIndex
        val dateString = SimpleDateFormat("dd/MM/yyyy").format(Date(patientHistory.date))
        patientDate.text = dateString
    }
}