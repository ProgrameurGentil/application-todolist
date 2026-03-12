package com.example.note_haie.database.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.EnumPriorityLevel

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "periodicy") val periodicy: EnumPeriodicyTask,
    @ColumnInfo(name = "priority") val priority: EnumPriorityLevel,
    @ColumnInfo(name = "date") val date: Long?,
    @ColumnInfo(name = "date_validated") val dateValidated: Long?,
    @ColumnInfo(name = "file") val file: String?,
    @ColumnInfo(name = "is_validated") val isValidated: Boolean
)