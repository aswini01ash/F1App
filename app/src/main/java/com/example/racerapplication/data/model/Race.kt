package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class RaceResponse(
    val races: List<Race>
)

@Serializable
data class Race(
    val round: Int,
    @SerialName("race_name") val raceName: String,
    @SerialName("circuit_id") val circuitId: String,
    @SerialName("race_start_time") val raceStartTime: String,
    @SerialName("race_end_time") val raceEndTime: String,
    val sessions: List<Session>
)

