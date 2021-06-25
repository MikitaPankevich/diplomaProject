package com.demo.diplomaproject.ui.main.settings.pacients

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.domain.entity.UserProfile
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_patient.*

class PatientsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView = itemView

    fun bind(userProfile: UserProfile, itemClickListener: (patient: UserProfile) -> Unit) {
        patientName.text = userProfile.name
        patientSurname.text = userProfile.surname
        patientEmail.text = userProfile.email
        patientShowHistoryButton.setOnClickListener { itemClickListener.invoke(userProfile) }
    }
}