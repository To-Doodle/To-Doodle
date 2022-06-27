package ca.uwaterloo.cs.todoodle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder


@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao

    companion object {

        private const val DATABASE_NAME = "task.db"
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase? {
            if (INSTANCE == null) {
                synchronized(TaskDatabase::class) {
                    INSTANCE = databaseBuilder(context,
                        TaskDatabase::class.java,
                        DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}