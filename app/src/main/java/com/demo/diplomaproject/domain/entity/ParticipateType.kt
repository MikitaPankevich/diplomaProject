package com.demo.diplomaproject.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class ParticipateType : Serializable {

    @SerializedName("DOCTOR")
    DOCTOR,

    @SerializedName("PATIENT")
    PATIENT,

    @SerializedName("CLEAR")
    CLEAR
}