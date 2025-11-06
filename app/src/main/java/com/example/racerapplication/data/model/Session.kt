package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("sessionName") val sessionName: String,
    @SerialName("startTime") val startTime: Long,
    @SerialName("endTime") val endTime: Long
)