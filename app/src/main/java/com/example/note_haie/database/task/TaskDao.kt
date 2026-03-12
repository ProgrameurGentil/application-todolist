package com.example.note_haie.database.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.note_haie.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)

    @Update
    suspend fun updateTask(task: TaskEntity): Int

    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE is_validated == True ORDER BY date ASC")
    fun getValidatedTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE is_validated == False ORDER BY date ASC")
    fun getNotValidatedTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id == :id")
    fun getTaskWithId(id: Int): Flow<TaskEntity?>
}