package com.example.note_haie.utils

import androidx.compose.animation.core.Spring
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Clock

enum class MonthName {
    JAN, FEV, MAR, AVR, MAI, JUI, JUIL, AOU, SEP, OCT, NOV, DEC
}

val MonthName.label: String
    get() = when (this) {
        MonthName.JAN  -> "Janvier"
        MonthName.FEV  -> "Fevrier"
        MonthName.MAR  -> "Mars"
        MonthName.AVR  -> "Avril"
        MonthName.MAI  -> "Mai"
        MonthName.JUI  -> "Kuin"
        MonthName.JUIL -> "Juillet"
        MonthName.AOU  -> "Aout"
        MonthName.SEP  -> "Septembre"
        MonthName.OCT  -> "Octobre"
        MonthName.NOV  -> "Novembre"
        else           -> "Decembre"
    }

private fun setMonth(number: Int): String {
    val months = MonthName.entries
    if (number > months.size || number < 0)
        return ""
    return MonthName.entries[number].label
}

data class DateTimeComponent(
    val year: Int,
    val month: String,
    val day: Int,
    val hour: Int,
    val minute: Int,
)

fun unixToUtc(unixTime: Long): String {
    val instant = Instant.ofEpochMilli(unixTime)
    val zoneUtc = instant.atZone(ZoneId.of("UTC"))
    val formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    return zoneUtc.format(formatDate)
}

fun decomposeUnixTime(unixTime: Long, zoneId: ZoneId = ZoneId.of("UTC")): DateTimeComponent {
    val instant = Instant.ofEpochMilli(unixTime)
    val zdt = instant.atZone(zoneId)

    return DateTimeComponent(
        year = zdt.year,
        month = setMonth(zdt.month.value),
        day = zdt.dayOfMonth,
        hour = zdt.hour,
        minute = zdt.minute
    )
}

fun decomposeUnixTime(unixTime: Long?, zoneId: ZoneId = ZoneId.of("UTC")): DateTimeComponent? {
    if (unixTime != null) {
        return decomposeUnixTime(unixTime)
    }
    return null
}

/**
 * Retourne la date (jour, mois, année) en format temps unix
 */
fun getDateWithUnixTime(unixTime: Long, zoneId: ZoneId = ZoneId.of("UTC")): Long {
    return Instant.ofEpochMilli(unixTime)
        .atZone(zoneId)
        .toLocalDate()
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun getDateWithUnixTime(unixTime: Long?, zoneId: ZoneId = ZoneId.of("UTC")): Long? {
    if (unixTime != null) {
        return getDateWithUnixTime(unixTime)
    }
    return null
}

fun getUnixTimeIsPassed(unixTime: Long): Boolean {
    val actualUnixTime = System.currentTimeMillis()
    return actualUnixTime > unixTime
}