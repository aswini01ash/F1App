package com.example.racerapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class DriverResponse(
    val drivers: List<Driver>
)
@Serializable
data class Driver(
    val position: Int,
    val name: String,
    val team: String,
    val points: Int,
    @SerialName("image_url")
    val imageUrl: String?
)