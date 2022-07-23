package ca.uwaterloo.cs.todoodle.ui.rewards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.model.Reward
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

// Must use AndroidViewModel to obtain the application context
class RewardsViewModel(application: Application, private val filename: String) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()

    private val achievementRepository = AchievementRepository(app, "achievements.json")

    private val rewards: LiveData<List<Reward>> by lazy {
        MutableLiveData<List<Reward>>().also {
            it.postValue(parseRewardsJSON())
        }
    }

    /**
     * Async rewards getter
     * @return Rewards observable
     */
    fun loadRewards(): LiveData<List<Reward>> {
        return rewards
    }

    /**
     * Load rewards data form local asset.
     * @return Parsed rewards in list of hashmap
     */
    private fun parseRewardsJSON(): List<Reward> {
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
        val listRewardType = object : TypeToken<List<Reward>>() {}.type

        return gson.fromJson(jsonString, listRewardType)
    }

    /**
     * Get user points
     * @return User points
     */
    fun getPoints(): Int {
        return achievementRepository.getPoints()
    }

}