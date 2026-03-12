package com.example.note_haie.database.task

import androidx.compose.runtime.mutableStateOf
import com.example.note_haie.model.EnumStateTask
import com.example.note_haie.model.EnumStateTimeTask
import com.example.note_haie.model.Task
import com.example.note_haie.utils.getUnixTimeIsPassed

/**
 * Fonction qui convertie un objet TaskEntity en Task
 */
fun TaskEntity.toDomain(): Task {
    val date = this.date

    val stateTime = when {
        date == null -> EnumStateTimeTask.NONE
        getUnixTimeIsPassed(date) -> EnumStateTimeTask.LATE
        else -> EnumStateTimeTask.IN_TIME
    }

    val state = when {
        this.isValidated -> EnumStateTask.REALISED
        else -> EnumStateTask.NOT_REALISED
    }

    return Task(
        id = this.id,
        name = this.title,
        date = this.date,
        dateValidated = this.dateValidated,
        description = this.description,
        isValidated = mutableStateOf(this.isValidated),
        stateTime = stateTime,
        state = state,
        periodicy = this.periodicy,
        file = this.file,
    )
}

/**
 * Fonction qui convertie un objet Task en TaskEntity
 */
fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.name,
        description = this.description,
        periodicy = this.periodicy,
        date = this.date,
        dateValidated = dateValidated,
        file = this.file,
        isValidated = this.isValidated.value
    )
}