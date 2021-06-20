package com.demo.diplomaproject.ui.main.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.domain.entity.TestResult
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView = itemView

    fun bind(testResult: TestResult) {

        val dateString = SimpleDateFormat("dd/MM/yyyy").format(Date(testResult.date))
        date.text = dateString
        result.text = testResult.qualityIndex
        maxRms.text = testResult.maxRms
        minRms.text = testResult.minRms
        averageRms.text = testResult.averageRms
    }
}