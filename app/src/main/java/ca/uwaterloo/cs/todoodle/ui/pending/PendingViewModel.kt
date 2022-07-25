package ca.uwaterloo.cs.todoodle.ui.pending

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ca.uwaterloo.cs.todoodle.data.AppDatabase
import ca.uwaterloo.cs.todoodle.data.TaskDao

// Must use AndroidViewModel to obtain the application context
class PendingViewModel(application: Application) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()
    private val appDB = AppDatabase.getInstance(app)
    private val taskDao = appDB.taskDao()

    /**
     * Get taskDao for dialog adapter
     * @return User points
     */
    fun getTaskDao(): TaskDao {
        return taskDao
    }

}