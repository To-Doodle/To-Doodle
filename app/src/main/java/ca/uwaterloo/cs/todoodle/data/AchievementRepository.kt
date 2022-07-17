package ca.uwaterloo.cs.todoodle.data

import android.app.Application
import ca.uwaterloo.cs.todoodle.data.model.Achievement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * This can be a 'repository' if we consider the assets as database
 */
class AchievementRepository(
    private val app: Application,
    private val filename: String,
) {
    // initialize dao and repository
    private val userDao = AppDatabase.getInstance(app).userDao()
    private val userRepository = UserRepository(userDao)
    private val loginRepository = LoginRepository(LoginDataSource())

    private val userId = 1
//    private val userId = loginRepository.user!!.userId

    val achievements = parseAchievementJSON()

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

    /**
     * Check in DB (or local copy) if the achievements have complete
     * @return A map of completed achievements in {<achievement_id>: 1}
     */
    fun getCompletedAchievements(): HashMap<String, Int> {
        /**
         * Query all completed achievements from the target
         * Loop through the achievements and change the `done` attribute
         * That's why we store the achievement info as hashmap/dictionary instead of an array/list in DB
         * We got O(n) complexity with hashmap but O(n^2) complexity with array
         */

        // Now we don't have user in the DB so fake it
//        val userId = 1
//        val userObj = userRepository.findById(userId)
//        val completedAchievements = userObj.completedAchievements
        val completedAchievements = hashMapOf<String, Int>()
        if (completedAchievements == null || completedAchievements.isEmpty()) {
            return hashMapOf()
        }

        return completedAchievements
    }

    /**
     * Check and update achievements after DB manipulation
     * @param userId user id
     */
    fun checkAndUpdateAchievements(userId: Int) {
        val completedAchievements = getCompletedAchievements()

        // Update the records if achievement was completed by the latest DB manipulation
        for ((id, _) in achievements) {
            if (id in completedAchievements) continue

            val shouldUpdate = checkAchievement(id)
            if (shouldUpdate) {
                completedAchievements[id] = 1
            }

            updateAchievements(completedAchievements)
        }
    }

    /**
     * Check various objects in DB to see if meets the requirement of achievement
     * @param achievementID The achievement to check
     * @return If meet the requirement
     */
    private fun checkAchievement(achievementID: String): Boolean {
        var result = false
        when (achievementID){
            "series_goal_1" -> result = "#goal >= 1" == ""
            "series_goal_2" -> result = "#goal >= 2" == ""
            // ...
        }

        return result
    }

    /**
     * Update the completed achievements
     * @param achievements The new completed achievements
     */
    private fun updateAchievements(achievements: HashMap<String, Int>) {
        userRepository.updateCompletedAchievements(userId, achievements)
    }
}