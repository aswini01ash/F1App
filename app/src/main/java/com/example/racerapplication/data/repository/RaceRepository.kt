package com.example.racerapplication.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.racerapplication.data.model.Driver
import com.example.racerapplication.data.model.Race
import com.example.racerapplication.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RaceRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getTopDriver(): Result<Driver> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDrivers()
            val topDriver = response.drivers.firstOrNull { it.position == 1 }
            if (topDriver != null) {
                Result.success(topDriver)
            } else {
                Result.failure(Exception("No driver found with position 1"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUpcomingRace(): Result<Race> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRaces()
            val currentTime = System.currentTimeMillis() / 1000

            val upcomingRace = response.schedule
                .filter { race -> race.raceStartTime > currentTime }
                .minByOrNull { race -> race.raceStartTime }

            if (upcomingRace != null) {
                Result.success(upcomingRace)
            } else {
                Result.failure(Exception("No upcoming race found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseIsoDateTime(dateTimeString: String): Long {
        return try {
            java.time.Instant.parse(dateTimeString).toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }

