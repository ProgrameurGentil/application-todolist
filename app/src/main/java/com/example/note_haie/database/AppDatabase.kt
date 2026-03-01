package com.example.note_haie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.note_haie.database.task.TaskDao
import com.example.note_haie.database.task.TaskEntity
import com.example.note_haie.model.EnumPeriodicyTask


class Converters {
    @TypeConverter
    fun fromEnum(periodicy: EnumPeriodicyTask): String {
        return periodicy.name
    }

    @TypeConverter
    fun toEnum(name: String): EnumPeriodicyTask {
        return EnumPeriodicyTask.valueOf(name)
    }
}

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}