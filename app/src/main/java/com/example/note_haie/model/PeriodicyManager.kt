package com.example.note_haie.model

import com.example.note_haie.utils.actualDate
import com.example.note_haie.utils.addTimeInDate
import com.example.note_haie.utils.decomposeUnixTime

enum class EnumPeriodicyTask {
    SINGLE, DAILY, WEEKLY, MONTHLY
}

val EnumPeriodicyTask.label: String
    get() = when (this) {
        EnumPeriodicyTask.SINGLE -> "Une seule fois"
        EnumPeriodicyTask.DAILY  -> "Tout les jours"
        EnumPeriodicyTask.WEEKLY -> "Une fois par semaine"
        else                     -> "Une fois par mois"
    }

/**
 * Donne une date en fonction de la periodicite donnee
 * @param periodicy - La periodicite
 * @param date - La date sous forme Unix millisecond
 * @return - Le nouveau temps sous forme Unix millisecond
 */
fun updateDateWithPeriodicy(periodicy: EnumPeriodicyTask, date: Long, dateValidated: Long?): Long {
    val localDate = decomposeUnixTime(actualDate())
    val dateValidatedTask = decomposeUnixTime(dateValidated)

    dateValidatedTask?.let {
        if (it.day == localDate.day && it.month == localDate.month && it.year == localDate.year) {
            return date
        }
    }

    return when (periodicy) {
        EnumPeriodicyTask.SINGLE -> {
             date
        }
        EnumPeriodicyTask.DAILY -> {
            addTimeInDate(date = date, day = 1)
        }
        EnumPeriodicyTask.WEEKLY -> {
            addTimeInDate(date = date, week = 1)
        }
        EnumPeriodicyTask.MONTHLY -> {
            addTimeInDate(date = date, month = 1)
        }
    }
}