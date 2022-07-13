package ca.uwaterloo.cs.todoodle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.*
import ca.uwaterloo.cs.todoodle.data.model.Achievement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

// Must use AndroidViewModel to obtain the application context
class AchievementsViewModel(application: Application, private val filename: String) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()

    private val achievements: LiveData<List<Achievement>> by lazy {
        MutableLiveData<List<Achievement>>().also {
            it.postValue(parseAchievementJSON())
        }
    }

    // initialize dao and repository
    private val userDao = AppDatabase.getInstance(app).userDao()
    private val userRepository = UserRepository(userDao)
    private val loginRepository = LoginRepository(LoginDataSource())

    /**
     * Async achievements getter
     * @return Achievements observable
     */
    fun loadAchievements(): LiveData<List<Achievement>> {
        return achievements
    }

    /**
     * Check in DB (or local copy) if the achievements have complete
     * @return A map of completed achievements in {<achievement_id>: 1}
     */
    private fun getCompletedAchievements(): HashMap<String, Int> {
        /**
         * Query all completed achievements from the target
         * Loop through the achievements and change the `done` attribute
         * That's why we store the achievement info as hashmap/dictionary instead of an array/list in DB
         * We got O(n) complexity with hashmap but O(n^2) complexity with array
         */

        // Now we don't have user in the DB so fake it
//        val userId = loginRepository.user!!.userId
        val userId = 1
        val userObj = userRepository.findById(userId)
//        val completedAchievements = userObj.completedAchievements
        val completedAchievements = hashMapOf<String, Int>()
        if (completedAchievements == null || completedAchievements.isEmpty()) {
            return hashMapOf()
        }

        return completedAchievements
    }

    /**
     * Update the completion status of achievements
     * @param achievements Achievements before update
     * @return Achievements after update
     */
    fun updateAchievementCompletionStatus(achievements: List<Achievement>): List<Achievement> {
        val completedAchievements = getCompletedAchievements()

        if (completedAchievements.isNotEmpty()) {
            for (achievement in achievements) {
                if (achievement.id in completedAchievements) {
                    achievement.done = true
                }
            }
        }

        return achievements
    }

    /**
     * Load achievement data form local asset.
     * @return Parsed achievements in list of hashmap
     */
    private fun parseAchievementJSON(): List<Achievement> {
        // Read asset file
        val jsonString: String
        try {
            jsonString = app.assets.open(filename).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return listOf()
        }

        // Parse JSON string
        val gson = Gson()
        val listAchievementType = object : TypeToken<List<Achievement>>() {}.type

        return gson.fromJson(jsonString, listAchievementType)
    }
}