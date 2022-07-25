package ca.uwaterloo.cs.todoodle.data

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import ca.uwaterloo.cs.todoodle.RecycleViewAdapter
import ca.uwaterloo.cs.todoodle.data.model.Achievement
import ca.uwaterloo.cs.todoodle.data.model.AchievementType
import ca.uwaterloo.cs.todoodle.data.model.Task
import ca.uwaterloo.cs.todoodle.data.model.TaskType
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * This can be a 'repository' if we consider the assets as database
 */
class AchievementRepository(
    private val app: Application,
    private val filename: String? = "",
) {
    // initialize dao and repository
    private val appDB = AppDatabase.getInstance(app)
    private val userDao = appDB.userDao()
    private val userRepository = UserRepository(userDao)
    private val loginRepository = LoginRepository(LoginDataSource())

    private val database = Firebase.database.reference

    private val userId = 1
//    private val userId = loginRepository.user!!.userId

    val achievements = parseAchievementJSON()

    /**
     * Load achievement data form local asset.
     * @return Parsed achievements in list of hashmap
     */
    private fun parseAchievementJSON(): List<Achievement> {
        if (filename == "" || filename == null) return listOf()

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
     * Get user points
     * @return User points
     */
    fun getPoints(): Int {
        /**
         * Query all completed achievements from the target
         * Loop through the achievements and change the `done` attribute
         * That's why we store the achievement info as hashmap/dictionary instead of an array/list in DB
         * We got O(n) complexity with hashmap but O(n^2) complexity with array
         */

        // Now we don't have user in the DB so fake it
//        val userId = 1
//        val userObj = userRepository.findById(userId)
//        val points = userObj.points

        return 0
//        return points
    }

    /**
     * Check and update achievements after DB manipulation
     * @param activity Activity for getting database
     * @param type Achievement type defined as enumeration
     * @param amount Used for deciding the level of achievement
     */
    fun checkAndUpdateAchievements(activity: FragmentActivity, type: AchievementType, amount: Int? = 0) {
        val completedAchievements = getCompletedAchievements()
        val typeID = type.id

        var points = 0

        // Skip the completed achievement with type SINGLE
        if (typeID.startsWith("single") && typeID in completedAchievements) return

        // Update the records if achievement was completed by the latest DB manipulation
        val completedAchievement = checkAchievement(activity, type, amount)
        if (completedAchievement != null) {
            completedAchievements[completedAchievement] = 1

            val achievement = achievements.find {
                it.id == completedAchievement
            }

            points += achievement!!.points
        }

        // Also check for achievement itself
        val numberOfCompletedAchievements = completedAchievements.size
        val completedSelf =
            checkAchievement(activity, AchievementType.ACHIEVEMENT, numberOfCompletedAchievements)
        if (completedSelf != null) {
            completedAchievements[completedSelf] = 1

            val achievement = achievements.find {
                it.id == completedSelf
            }
            points += achievement!!.points
        }

        updateAchievements(completedAchievements, points)
    }

    /**
     * Check various objects in DB to see if meets the requirement of achievement
     * @param activity Activity for getting database
     * @param type Achievement type defined as enumeration
     * @param amount Used for deciding the level of achievement
     * @return The achievement ID that should be mark as completed
     */
    private fun checkAchievement(
        activity: Activity,
        type: AchievementType,
        amount: Int? = 0
    ): String? {
        var result: String? = null
        when (type) {
            AchievementType.GOAL -> {
                result = when (amount) {
                    in 20..Int.MAX_VALUE -> type.id + "_3"
                    in 5..19 -> type.id + "_2"
                    in 1..4 -> type.id + "_1"
                    else -> null
                }
            }
            AchievementType.TASK -> {
                database.child("tasks").get().addOnSuccessListener { dataSnapshot ->
                    val sharedPreferences = activity.getSharedPreferences(SHAREDPREF_FILENAME, Context.MODE_PRIVATE)
                    val userKey = sharedPreferences.getString("key", "defaultKey")

                    var numberOfTask = 0

                    for (postSnapshot in dataSnapshot.children) {
                        // TODO: handle the post
                        val task = postSnapshot.getValue(Task::class.java)
                        if (task!!.userKey == userKey) {
                            numberOfTask++
                        }
                    }

                    result = when (numberOfTask) {
                        in 50..Int.MAX_VALUE -> type.id + "_3"
                        in 10..49 -> type.id + "_2"
                        in 1..9 -> type.id + "_1"
                        else -> null
                    }
                }

            }
            AchievementType.DUE -> {
                database.child("tasks").get().addOnSuccessListener { dataSnapshot ->
                    val sharedPreferences = activity.getSharedPreferences(SHAREDPREF_FILENAME, Context.MODE_PRIVATE)
                    val userKey = sharedPreferences.getString("key", "defaultKey")

                    var numberOfTask = 0

                    for (postSnapshot in dataSnapshot.children) {
                        // TODO: handle the post
                        val task = postSnapshot.getValue(Task::class.java)
                        if (task!!.userKey == userKey && task!!.status == TaskType.COMPLETED) {
                            numberOfTask++
                        }
                    }

                    result = when (numberOfTask) {
                        in 50..Int.MAX_VALUE -> type.id + "_3"
                        in 10..49 -> type.id + "_2"
                        in 1..9 -> type.id + "_1"
                        else -> null
                    }
                }
            }
            AchievementType.ACHIEVEMENT -> {
                result = when (amount) {
                    in 12..Int.MAX_VALUE -> type.id + "_3"
                    in 8..11 -> type.id + "_2"
                    in 3..7 -> type.id + "_1"
                    else -> null
                }
            }
            else -> {
                // For SINGLE achievement return its id
                result = type.id
            }
        }

        return result
    }

    /**
     * Update the completed achievements
     * @param achievements The new completed achievements
     * @param points Points to add on
     */
    private fun updateAchievements(achievements: HashMap<String, Int>, points: Int) {
        userRepository.updateCompletedAchievements(userId, achievements)
        userRepository.updatePoints(userId, points)
    }

    /**
     * Add points for completing a task
     */
    fun updatePointsForCompletion() {
        // Fixed 20 AP each task
        userRepository.updatePoints(userId, 20)
    }
}