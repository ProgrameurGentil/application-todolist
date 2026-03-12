package com.example.note_haie.utils

import androidx.compose.animation.core.Spring
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.min
import kotlin.time.Clock

private enum class TimeInMs {
    MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
}

private val TimeInMs.value : Long
    get() = when (this) {
        TimeInMs.YEAR -> 31_556_926_000
        TimeInMs.MONTH -> 2_629_743_000
        TimeInMs.WEEK -> 604_800_000
        TimeInMs.DAY -> 86_400_000
        TimeInMs.HOUR -> 3_600_000
        TimeInMs.MINUTE -> 60_000
    }


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

/**
 * Fonction qui retourne le temps Unix sous forme d'un chaine 'JJ/MM/AAAA' ou 'JJ/MM/AAAA à HH/mm'
 * Args:
 *  unixTime: Long -> le temps en Unix milliseconde
 *  time: Boolean = false -> si 'time' est vrai (true), retourne sous forme 'JJ/MM/AAAA à HH/mm' ("dd/MM/yyyy' à 'HH'h'mm")
 *                           si 'time" est faux (false), retourne sous forme 'JJ/MM/AAAA' ("dd/MM/yyyy")
 *  Return:
 *   String -> une chaine de carateres
 */
fun unixToUtc(unixTime: Long, time: Boolean = false): String {
    val instant = Instant.ofEpochMilli(unixTime)
    val zoneUtc = instant.atZone(ZoneId.systemDefault())
    val formatDate = if (time) DateTimeFormatter.ofPattern("dd/MM/yyyy' à 'HH'h'mm") else DateTimeFormatter.ofPattern("dd/MM/yyyy")

    return zoneUtc.format(formatDate)
}

fun decomposeUnixTime(unixTime: Long, zoneId: ZoneId = ZoneId.systemDefault()): DateTimeComponent {
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

fun decomposeUnixTime(unixTime: Long?, zoneId: ZoneId = ZoneId.systemDefault()): DateTimeComponent? {
    if (unixTime != null) {
        return decomposeUnixTime(unixTime, zoneId = zoneId)
    }
    return null
}

/**
 * Prends la date Unix milliseconde et là retourne en enlevant les heures, minutes, secondes, ...
 * Args :
 *  unixTime:Long -> la date en format Unix milliseconde
 *  zoneId: ZoneId = ZoneId.systemDefault() -> UTC
 * Returns:
 *  Long -> retourne la date (jour, mois, année) en format temps unix
 */
fun getDateWithUnixTime(unixTime: Long, zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return Instant.ofEpochMilli(unixTime)
        .atZone(zoneId)
        .toLocalDate()
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun getDateWithUnixTime(unixTime: Long?, zoneId: ZoneId =  ZoneId.systemDefault()): Long? {
    if (unixTime != null) {
        return getDateWithUnixTime(unixTime)
    }
    return null
}

fun getUnixTimeIsPassed(unixTime: Long): Boolean {
    val actualUnixTime = System.currentTimeMillis()
    return actualUnixTime > unixTime
}

/**
 * Donne le temps en Unix millisecond en fonction de la date, de l'heure et de la minute
 * @param date le jour, le mois et l'annee sous le forme Unix en millisecond
 * @param hour l'heure sous la forme d'un nombre entier entre 0 et 23
 * @param minute la minute sous la forme d'un nombre entier entre 0 et 59
 *
 * @return Long - Le temps en format Unix. Si 'date' est null, alors la date sera remplacer par celle du jour.
 */
fun getUnixTimeWithDecomposedTime(date: Long?, hour: Int?, minute: Int?): Long {

    if (date == null && hour == null && minute == null) return  0

    val zoneId = ZoneId.systemDefault()

    val localDate = if (date != null) {
        Instant.ofEpochMilli(date).atZone(zoneId).toLocalDate()
    } else {
        LocalDate.now()
    }

    val dateValue = localDate.atStartOfDay(zoneId).toInstant().toEpochMilli()

    val hourValue = (hour ?: 0) * TimeInMs.HOUR.value
    val minuteValue = (minute ?: 0) * TimeInMs.MINUTE.value

    return dateValue + hourValue + minuteValue
}

/**
 * Ajoute du temps a du temps
 * @param date le temps au format Unix millisecond
 * @param year l'annee comprise dans un entier de 32 bis
 * @param month le mois compris dans un entier de 32 bits
 * @param week le mois compris dans un entier de 32 bits
 * @param day le jour compris dans un entier de 32 bits
 * @param hour l'heure comprise dans un entier de 32 bits
 * @param minute la minute comprise dans un entier de 32 bits
 * @return Long - Le nouveau temps au format Unix millisecond
 */
fun addTimeInDate(date: Long, year: Int = 0, month: Int = 0, week:Int = 0, day: Int = 0, hour: Int = 0, minute: Int = 0): Long {
    return date +
            year * TimeInMs.YEAR.value +
            month * TimeInMs.MONTH.value +
            week * TimeInMs.WEEK.value +
            day * TimeInMs.DAY.value +
            hour * TimeInMs.HOUR.value +
            minute * TimeInMs.MINUTE.value
}