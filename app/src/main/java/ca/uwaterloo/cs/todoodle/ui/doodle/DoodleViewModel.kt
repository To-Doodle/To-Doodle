package ca.uwaterloo.cs.todoodle.ui.doodle

import android.app.Activity
import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.model.AchievementType

class DoodleViewModel(application: Application) :
    AndroidViewModel(application) {

    private val app = getApplication<Application>()

    private val achievementRepository = AchievementRepository(app, "achievements.json")

    private val _text = MutableLiveData<String>().apply {
        value = "Draw a doodle!"
    }
    val text: LiveData<String> = _text

    /**
     * Update achievements of doodle
     * @param activity Activity for getting database
     * @param amount Number of doodles saved
     */
    fun updateAchievements(activity: FragmentActivity, amount: Int) {
        achievementRepository.checkAndUpdateAchievements(activity, AchievementType.GOAL, amount)
    }

    /**
     * Get user points
     * @return user points
     */
    fun getPoints(): Int {
        return achievementRepository.getPoints()
    }
}