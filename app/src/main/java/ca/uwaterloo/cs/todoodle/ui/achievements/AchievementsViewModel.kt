package ca.uwaterloo.cs.todoodle.ui.achievements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.*
import ca.uwaterloo.cs.todoodle.data.model.Achievement

<<<<<<< HEAD
<<<<<<< HEAD
// Must use AndroidViewModel to obtain the application context
=======
>>>>>>> Apply MVVM to achievements
=======
// Must use AndroidViewModel to obtain the application context
>>>>>>> Add achievement status handler
class AchievementsViewModel(application: Application, private val filename: String) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()

    private val achievementRepository = AchievementRepository(app, filename)

    private val achievements: LiveData<List<Achievement>> by lazy {
        MutableLiveData<List<Achievement>>().also {
            it.postValue(achievementRepository.achievements)
        }
    }

    /**
     * Async achievements getter
<<<<<<< HEAD
<<<<<<< HEAD
     * @return Achievements observable
=======
>>>>>>> Apply MVVM to achievements
=======
     * @return Achievements observable
>>>>>>> Add achievement status handler
     */
    fun loadAchievements(): LiveData<List<Achievement>> {
        return achievements
    }

    /**
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
     * Check in DB (or local copy) if the achievements have complete
     * @return A map of completed achievements in {<achievement_id>: 1}
=======
     * Check in DB (or local copy) if the achievements have complete
<<<<<<< HEAD
     * @return A map of completed achievements in {[achievement_id]: 1}
>>>>>>> Add achievement status handler
=======
     * @return A map of completed achievements in {<achievement_id>: 1}
>>>>>>> Totally refactor rewards to achievement
     */
    private fun getCompletedAchievements(): HashMap<String, Int> {
        /**
         * Query all completed achievements from the target
         * Loop through the achievements and change the `done` attribute
         * That's why we store the achievement info as hashmap/dictionary instead of an array/list in DB
<<<<<<< HEAD
<<<<<<< HEAD
         * We got O(n) complexity with hashmap but O(n^2) complexity with array
=======
>>>>>>> Add achievement status handler
=======
         * We got O(n) complexity with hashmap but O(n^2) complexity with array
>>>>>>> Totally refactor rewards to achievement
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
=======
>>>>>>> Create achievement repository
     * Update the completion status of achievements
     * @param achievements Achievements before update
     * @return Achievements after update
     */
    fun updateAchievementCompletionStatus(achievements: List<Achievement>): List<Achievement> {
        val completedAchievements = achievementRepository.getCompletedAchievements()

        if (completedAchievements.isNotEmpty()) {
            for (achievement in achievements) {
                if (achievement.id in completedAchievements) {
                    achievement.done = true
                }
            }
        }

        return achievements
    }
<<<<<<< HEAD
<<<<<<< HEAD

    /**
<<<<<<< HEAD
     * Load achievement data form local asset.
     * @return Parsed achievements in list of hashmap
=======
     * Load achievement data form local asset.
>>>>>>> Apply MVVM to achievements
=======
     * Load achievement data form local asset.
     * @return Parsed achievements in list of hashmap
>>>>>>> Add achievement status handler
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
=======
>>>>>>> Create achievement repository
=======

    /**
     * Get user points
     * @return User points
     */
    fun getPoints(): Int {
        return achievementRepository.getPoints()
    }

>>>>>>> Add points retriever. Display in achievement page
}