package com.example.note_haie.model

import com.example.note_haie.utils.addTimeInDate

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
 * Donne une date en fonction de la periodicite donne
 * @param periodicy - La periodicite
 * @param date - La date sous forme Unix millisecond
 * @return - Le nouveau temps sous forme Unix millisecond
 */
fun updateDateWithPeriodicy(periodicy: EnumPeriodicyTask, date: Long): Long {
    when (periodicy) {
        EnumPeriodicyTask.SINGLE -> {
            return date
        }
        EnumPeriodicyTask.DAILY -> {
            return addTimeInDate(date = date, day = 1)
        }
        EnumPeriodicyTask.WEEKLY -> {
            return addTimeInDate(date = date, week = 1)
        }
        EnumPeriodicyTask.MONTHLY -> {
            return addTimeInDate(date = date, month = 1)
        }
    }
}