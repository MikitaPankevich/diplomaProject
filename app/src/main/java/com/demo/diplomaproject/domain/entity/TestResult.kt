package com.demo.diplomaproject.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestResult(
    @SerializedName("averageRms") val averageRms: String,
    @SerializedName("minRms") val minRms: String,
    @SerializedName("maxRms") val maxRms: String,
    @SerializedName("qualityIndex") val qualityIndex: String,
    @SerializedName("date") val date: Long,
) : Parcelable