package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("session_name")
    val sessionName: String,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String
)