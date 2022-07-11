package ca.uwaterloo.cs.todoodle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.model.Achievement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class AchievementsViewModel(application: Application, private val filename: String) :
    AndroidViewModel(application) {
    private val app = getApplication<Application>()

    private val achievements: LiveData<List<Achievement>> by lazy {
        MutableLiveData<List<Achievement>>().also {
            parseAchievementJSON()
        }
    }

    /**
     * Async achievements getter
     */
    fun loadAchievements(): LiveData<List<Achievement>> {
        return achievements
    }

    /**
     * Load achievement data form local asset.
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