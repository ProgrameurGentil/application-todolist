package com.example.note_haie.model

import androidx.compose.runtime.MutableState

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

data class Task(
    val name: String,
    val date: String,
    val description: String,
    val isValidated: MutableState<Boolean>,
    val stateTime: EnumStateTimeTask,
    val state: EnumStateTask
)

object ExempleTask {
    val tasks = listOf(
        Task("Tache1", "Date", "Description tache 1", androidx.compose.runtime.mutableStateOf(false), EnumStateTimeTask.NONE, EnumStateTask.NOT_REALISED),
        Task("Tache2", "Date", "Description tache 2", androidx.compose.runtime.mutableStateOf(false), EnumStateTimeTask.IN_TIME, EnumStateTask.NOT_REALISED),
        Task("Tache3", "Date", "Description tache 3", androidx.compose.runtime.mutableStateOf(false), EnumStateTimeTask.LATE, EnumStateTask.NOT_REALISED),
        Task("Nom de la tache", "Date", "Description", androidx.compose.runtime.mutableStateOf(true), EnumStateTimeTask.NONE, EnumStateTask.REALISED)
    )
}
