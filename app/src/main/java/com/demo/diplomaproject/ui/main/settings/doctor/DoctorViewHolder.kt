package com.demo.diplomaproject.ui.main.settings.doctor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.domain.entity.UserProfile
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_doctor.*

class DoctorViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView = itemView

    fun bind(userProfile: UserProfile) {
        itemName.text = userProfile.name
        itemSurname.text = userProfile.surname
        itemUniversity.text = userProfile.university.orEmpty()
        itemExperience.text = userProfile.experience.orEmpty()
    }
}