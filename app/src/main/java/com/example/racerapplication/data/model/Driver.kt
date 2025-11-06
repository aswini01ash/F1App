package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class DriverResponse(
    val drivers: List<Driver>
)

@Serializable
data class Driver(
    val position: Int,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("teamName") val teamName: String,
    val points: Int,
    @SerialName("driverId") val driverId: String? = null
) {
    // Computed property for full name
    val name: String
        get() = "$firstName $lastName"

    val team: String
        get() = teamName
}