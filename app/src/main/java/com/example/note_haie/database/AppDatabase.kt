package com.example.note_haie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.note_haie.database.task.TaskDao
import com.example.note_haie.database.task.TaskEntity
import com.example.note_haie.model.EnumPeriodicyTask
import com.example.note_haie.model.EnumPriorityLevel
import com.example.note_haie.model.value


class Converters {
    @TypeConverter
    fun fromPeriodicy(periodicy: EnumPeriodicyTask): String {
        return periodicy.name
    }

    @TypeConverter
    fun toPeriodicy(name: String): EnumPeriodicyTask {
        return EnumPeriodicyTask.valueOf(name)
    }

    @TypeConverter
    fun fromPriority(priority: EnumPriorityLevel): Int {
        return priority.value // On stocke l'entier (1, 2 ou 3)
    }

    @TypeConverter
    fun toPriority(value: Int): EnumPriorityLevel {
        return when (value) {
            1 -> EnumPriorityLevel.LVL1
            2 -> EnumPriorityLevel.LVL2
            3 -> EnumPriorityLevel.LVL3
            else -> EnumPriorityLevel.LVL1
        }
    }
}


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE tasks ADD COLUMN date_validated INTEGER DEFAULT NULL")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE tasks ADD COLUMN priority INTEGER NOT NULL DEFAULT 1")
    }
}

@Database(entities = [TaskEntity::class], version = 3)
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
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
