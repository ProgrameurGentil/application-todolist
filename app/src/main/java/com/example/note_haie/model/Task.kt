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

enum class EnumPriorityLevel {
    LVL1, LVL2, LVL3
}

val EnumPriorityLevel.label: String
    get() = when (this) {
        EnumPriorityLevel.LVL1 -> "Niv. Bas"
        EnumPriorityLevel.LVL2 -> "Niv. Moyen"
        EnumPriorityLevel.LVL3 -> "Niv. Elevé"
    }

val EnumPriorityLevel.value: Int
    get() = when (this) {
        EnumPriorityLevel.LVL1 -> 1
        EnumPriorityLevel.LVL2 -> 2
        EnumPriorityLevel.LVL3 -> 3
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
    val priority: EnumPriorityLevel,
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
 * @param priority la priorite (Lvl1, Lvl2, Lvl3)
 * @param file l'url du fichier associe
 * @return Task - une tache
 */
fun copyTask(
    task: Task,
    name: String = task.name,// Par défaut, on prend la valeur actuelle
    date: Long? = task.date,
    dateValidated: Long? = task.dateValidated,
    description: String = task.description,
    isValidated: MutableState<Boolean> = task.isValidated,
    stateTime: EnumStateTimeTask = task.stateTime,
    state: EnumStateTask = task.state,
    periodicy: EnumPeriodicyTask = task.periodicy,
    priority: EnumPriorityLevel = task.priority,
    file: String? = task.file                 // Par défaut, on prend l'ancien fichier
): Task {
    return Task(
        id = task.id,
        name = name,
        date = date,
        dateValidated = dateValidated,
        description = description,
        isValidated = isValidated,
        stateTime = stateTime,
        state = state,
        periodicy = periodicy,
        priority = priority,
        file = file
    )
}

// pour les tests
object ExempleTask {
    val tasks = listOf(
        Task(0,"Tache1", 1772318526000, null, "Description tache 1",
            mutableStateOf(false), EnumStateTimeTask.NONE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.SINGLE, EnumPriorityLevel.LVL1),
        Task(1,"Tache2", 1772318526000, null, "Description tache 2",
            mutableStateOf(false), EnumStateTimeTask.IN_TIME, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.WEEKLY, EnumPriorityLevel.LVL1),
        Task(2,"Tache3", 1772318526000, null, "Description tache 3",
            mutableStateOf(false), EnumStateTimeTask.LATE, EnumStateTask.NOT_REALISED, EnumPeriodicyTask.MONTHLY, EnumPriorityLevel.LVL1),
        Task(3,"Nom de la tache", 1772318526000, null, "Description",
            mutableStateOf(true), EnumStateTimeTask.NONE, EnumStateTask.REALISED, EnumPeriodicyTask.DAILY, EnumPriorityLevel.LVL1)
    )
}
