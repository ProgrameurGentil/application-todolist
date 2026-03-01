package com.example.note_haie.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.note_haie.database.task.TaskEntity
import com.example.note_haie.utils.getUnixTimeIsPassed

enum class EnumStateTimeTask {
    LATE, IN_TIME, NONE
}

val EnumStateTimeTask.label: String
    get() = when (this) {
        EnumStateTimeTask.LATE -> "En retard"
        else -> "Dans les temps"
    }

enum class EnumStateTask {
    REALISED, NOT_REALISED
}

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

data class Task(
    val id: Int,
    val name: String,
    val date: Long?,
    val description: String,
    val isValidated: MutableState<Boolean>,
    val stateTime: EnumStateTimeTask,
    val state: EnumStateTask,
    val periodicy: EnumPeriodicyTask,
    val file: String? = null
)

object ExempleTask {
    val tasks = listOf(
        Task(0,"Tache1", 1772318526000, "Description tache 1",
            mutableStateOf(false), EnumStateTimeTask.NONE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.SINGLE),
        Task(1,"Tache2", 1772318526000, "Description tache 2",
            mutableStateOf(false), EnumStateTimeTask.IN_TIME, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.WEEKLY),
        Task(2,"Tache3", 1772318526000, "Description tache 3",
            mutableStateOf(false), EnumStateTimeTask.LATE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.MONTHLY),
        Task(3,"Nom de la tache", 1772318526000, "Description",
            mutableStateOf(true), EnumStateTimeTask.NONE, EnumStateTask.REALISED, EnumPeriodicyTask.DAILY)
    )
}

