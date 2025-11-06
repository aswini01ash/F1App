package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class RaceResponse(
    val schedule: List<Race>
)

@Serializable
data class Race(
    val round: Int,
    @SerialName("raceName") val raceName: String,
    @SerialName("circuitId") val circuitId: String,
    @SerialName("raceStartTime") val raceStartTime: Long,
    @SerialName("raceEndTime") val raceEndTime: Long,
    val sessions: List<Session>,
    @SerialName("raceState") val raceState: String? = null
)