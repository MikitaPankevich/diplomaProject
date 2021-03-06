package com.demo.diplomaproject.ui.main.settings.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.R
import com.demo.diplomaproject.domain.entity.UserProfile

class DoctorAdapter(
    private val itemClickListener: (email: String) -> Unit
) : RecyclerView.Adapter<DoctorViewHolder>() {

    private val doctors = mutableListOf<UserProfile>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_doctor, parent, false)
        val holder = DoctorViewHolder(view)

        view.setOnClickListener {
            val position = holder.adapterPosition
            val doctor = doctors[position]
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.invoke(doctor.email)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(doctors[position])
    }

    override fun getItemCount() = doctors.size

    fun showHistory(doctors: List<UserProfile>) {
        this.doctors.clear()
        this.doctors.addAll(doctors)
        notifyDataSetChanged()
    }
}