package com.example.note_haie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note_haie.database.task.TaskDao
import com.example.note_haie.database.task.TaskEntity
import com.example.note_haie.database.task.toDomain
import com.example.note_haie.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {

    fun fullTasks(): Flow<List<Task>> = taskDao.getAllTasks()
        .map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun deleteTask(id: Int) = taskDao.deleteTask(id)

    suspend fun updateTask(taskEntity: TaskEntity) = taskDao.updateTask(taskEntity)

    fun validatedTask(): Flow<List<Task>> = taskDao.getValidatedTask()
        .map { entities ->
            entities.map { it.toDomain() }
        }

    fun notValidatedTask(): Flow<List<Task>> = taskDao.getNotValidatedTask()
        .map { entities ->
            entities.map { it.toDomain() }
        }

    fun getTaskWithId(id: Int): Task = taskDao.getTaskWithId(id).toDomain()

    suspend fun insertTask(taskEntity: TaskEntity) = taskDao.insert(taskEntity)
}

class TaskViewModelFactory(private val taskDao: TaskDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}