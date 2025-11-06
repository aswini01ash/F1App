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
    fun formatToLocalTime(isoDateTime: String): String {
        return try {
            val instant = Instant.parse(isoDateTime)
            val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            "N/A"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToDate(isoDateTime: String): String {
        return try {
            val instant = Instant.parse(isoDateTime)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            "N/A"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextSession(sessions: List<Session>): Session? {
        val currentTime = System.currentTimeMillis()
        return sessions
            .filter { session ->
                try {
                    Instant.parse(session.startTime).toEpochMilli() > currentTime
                } catch (e: Exception) {
                    false
                }
            }
            .minByOrNull { session ->
                try {
                    Instant.parse(session.startTime).toEpochMilli()
                } catch (e: Exception) {
                    Long.MAX_VALUE
                }
            }
    }
}
