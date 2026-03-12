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


data class Task(
    val id: Int,
    val name: String,
    val date: Long?,
    val dateValidated: Long? = null,
    val description: String,
    val isValidated: MutableState<Boolean>,
    val stateTime: EnumStateTimeTask,
    val state: EnumStateTask,
    val periodicy: EnumPeriodicyTask,
    val file: String? = null
)

/**
 * Copie une tache. On peut modifier un champ
 * @param task la tache a copier
 * @param name le nom
 * @param date la date au format Unix millisecond
 * @param dateValidated la date au format Unix millisecond de la validation de la taches
 * @param description la descripition
 * @param isValidated variable mutable concernant la validite d'une tache
 * @param stateTime l'etat au niveau de temps (en retard, a l'heure, rien)
 * @param state l'etat de la tache (valider, non valider)
 * @param periodicy la pariodicite
 * @param file l'url du fichier associe
 * @return Task - une tache
 */
fun copyTask(
    task: Task,
    name: String? = null,
    date: Long? = null,
    dateValidated: Long? = null,
    description: String? = null,
    isValidated: MutableState<Boolean>? = null,
    stateTime: EnumStateTimeTask? = null,
    state: EnumStateTask? = null,
    periodicy: EnumPeriodicyTask? = null,
    file: String? = null): Task {
    return Task(
        id = task.id,
        name = name ?: task.name,
        date = date ?: task.date,
        dateValidated = dateValidated ?: task.dateValidated,
        description = description ?: task.description,
        isValidated = isValidated ?: task.isValidated,
        stateTime = stateTime ?: task.stateTime,
        state = state ?: task.state,
        periodicy = periodicy ?: task.periodicy,
        file = file ?: task.file
    )

}

object ExempleTask {
    val tasks = listOf(
        Task(0,"Tache1", 1772318526000, null, "Description tache 1",
            mutableStateOf(false), EnumStateTimeTask.NONE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.SINGLE),
        Task(1,"Tache2", 1772318526000, null, "Description tache 2",
            mutableStateOf(false), EnumStateTimeTask.IN_TIME, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.WEEKLY),
        Task(2,"Tache3", 1772318526000, null, "Description tache 3",
            mutableStateOf(false), EnumStateTimeTask.LATE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.MONTHLY),
        Task(3,"Nom de la tache", 1772318526000, null, "Description",
            mutableStateOf(true), EnumStateTimeTask.NONE, EnumStateTask.REALISED, EnumPeriodicyTask.DAILY)
    )
}
