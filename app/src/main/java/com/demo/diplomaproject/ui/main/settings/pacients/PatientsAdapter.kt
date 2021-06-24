package com.demo.diplomaproject.ui.main.settings.pacients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.diplomaproject.R
import com.demo.diplomaproject.domain.entity.UserProfile

class PatientsAdapter(
    private val itemClickListener: (patient: UserProfile) -> Unit
) : RecyclerView.Adapter<PatientsViewHolder>() {

    private val patients = mutableListOf<UserProfile>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_patient, parent, false)
        return PatientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientsViewHolder, position: Int) {
        holder.bind(patients[position], itemClickListener)
    }

    override fun getItemCount() = patients.size

    fun showPatients(patients: List<UserProfile>) {
        this.patients.clear()
        this.patients.addAll(patients)
        notifyDataSetChanged()
    }
}