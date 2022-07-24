package ca.uwaterloo.cs.todoodle

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.SHAREDPREF_FILENAME
import ca.uwaterloo.cs.todoodle.data.model.AchievementType
import ca.uwaterloo.cs.todoodle.data.model.Reward
import ca.uwaterloo.cs.todoodle.data.model.Task
import ca.uwaterloo.cs.todoodle.data.model.TaskType
import ca.uwaterloo.cs.todoodle.databinding.FragmentCreateTaskFormBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

// Must use AndroidViewModel to obtain the application context
class CreateTaskFormViewModel(application: Application) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()

    private val achievementRepository = AchievementRepository(app)

    /**
     * Validate form input.
     * @param binding View binding to get form data
     * @return validated form data
     */
    fun validatedForm(binding: FragmentCreateTaskFormBinding): Bundle? {

        // Very basic validation. May use third-party lib for this.
        val requiredText = "Required"
        if (binding.createTaskFormName.text.isEmpty()) {
            binding.createTaskFormName.error = requiredText
            return null
        }
        if (binding.createTaskFormDdl.text.isEmpty()) {
            binding.createTaskFormDdl.error = requiredText
            return null
        }
        // > 1 days and 1-23 hours and no leading zeroes
        val regex = "^([1-9]\\d*d)?((1\\d?|2[0-3]?|[3-9])h)?\$".toRegex()
        if (!regex.matches(binding.createTaskFormDuration.text)) {
            binding.createTaskFormDuration.error = "Invalid format"
            return null
        }

        return bundleOf(
            "name" to binding.createTaskFormName.text,
            "cat" to binding.createTaskFormCat.selectedItem,
            "ddl" to binding.createTaskFormDdl.text,
            "duration" to binding.createTaskFormDuration.text,
            "goal" to binding.createTaskFormGoal.selectedItem,
            "level" to binding.createTaskFormLevel.selectedItem,
            "note" to binding.createTaskFormNote.text,
        )
    }

    /**
     * Create task in DB
     * @param formData task data
     */
    fun createTask(formData: Bundle) {
        val sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences(SHAREDPREF_FILENAME, Context.MODE_PRIVATE)
        val userKey = sharedPreferences.getString("key", "defaultKey")
        println(userKey)

        val database = Firebase.database.reference

        val task1 = Task(
            formData.get("name").toString(),
            formData.get("ddl").toString(),
            formData.get("cat").toString(),
            formData.get("duration").toString(),
            formData.get("level").toString(),
            formData.get("note").toString(),
            TaskType.IN_PROGRESS,
            userKey
        )
        database.child("tasks").child(System.currentTimeMillis().toString()).setValue(task1)

        // Update achievements
        achievementRepository.checkAndUpdateAchievements(AchievementType.TASK)
    }
}