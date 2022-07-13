package ca.uwaterloo.cs.todoodle.data

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

@Database(entities = [User::class, Task::class], version = 2)
@TypeConverters(AchievementConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {

        private const val TAG = "AppDatabase"

        const val VERSION = 2
        private const val DATABASE_NAME = "todoodle.db"

        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Gets and returns the database instance if exists; otherwise, builds a new database.
         * @param context The context to access the application context.
         * @return The database instance.
         */
        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        /**
         * Builds and returns the database.
         * @param appContext The application context to reference.
         * @return The built database.
         */
        private fun buildDatabase(appContext: Context): AppDatabase {
            val filesDir = appContext.getExternalFilesDir(null)
            val dataDir = File(filesDir, "data")
            if (!dataDir.exists())
                dataDir.mkdir()

            val builder =
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().fallbackToDestructiveMigration()

            return builder.build()
        }
    }
}

/**
 * This allows us to store data in hashmap which can be populated directly
 */
object AchievementConverter {
    @TypeConverter
    @JvmStatic
    fun parse(value: String): HashMap<String, Int> {
        return Gson().fromJson(value, object : TypeToken<HashMap<String, Int>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun stringify(value: HashMap<String, Int>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}
