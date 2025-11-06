package com.example.racerapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.racerapplication.data.model.Session
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToLocalTime(epochSeconds: Long): String {
        return try {
            val instant = Instant.ofEpochSecond(epochSeconds)
            val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            e.printStackTrace()
            "N/A"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToDate(epochSeconds: Long): String {
        return try {
            val instant = Instant.ofEpochSecond(epochSeconds)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            e.printStackTrace()
            "N/A"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToDateRange(startEpoch: Long, endEpoch: Long): String {
        return try {
            val startInstant = Instant.ofEpochSecond(startEpoch)
            val endInstant = Instant.ofEpochSecond(endEpoch)
            val formatter = DateTimeFormatter.ofPattern("dd", Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())
                .withZone(ZoneId.systemDefault())

            val startDay = formatter.format(startInstant)
            val endDay = formatter.format(endInstant)
            val month = monthFormatter.format(endInstant)

            "$startDay - $endDay $month"
        } catch (e: Exception) {
            e.printStackTrace()
            "N/A"
        }
    }

    fun getNextSession(sessions: List<Session>): Session? {
        val currentTime = System.currentTimeMillis() / 1000 // Convert to seconds
        return sessions
            .filter { session -> session.startTime > currentTime }
            .minByOrNull { session -> session.startTime }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeUntilSession(sessionStartTime: Long): Triple<Long, Long, Long> {
        val currentTime = System.currentTimeMillis() / 1000
        val diffSeconds = sessionStartTime - currentTime

        if (diffSeconds <= 0) {
            return Triple(0, 0, 0)
        }

        val days = diffSeconds / 86400
        val hours = (diffSeconds % 86400) / 3600
        val minutes = (diffSeconds % 3600) / 60

        return Triple(days, hours, minutes)
    }
}

