package com.demo.diplomaproject.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    @SerializedName("name") val name: String? = "",
    @SerializedName("surname") val surname: String? = "",
    @SerializedName("role") val role: ParticipateType? = ParticipateType.CLEAR,
    @SerializedName("expirience") val experience: String? = "",
    @SerializedName("university") val university: String? = ""
) : Parcelable