package ca.uwaterloo.cs.todoodle.ui.doodle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.data.model.AchievementType

class DoodleViewModel(application: Application) :
    AndroidViewModel(application) {

    private val app = getApplication<Application>()

    private val _text = MutableLiveData<String>().apply {
        value = "Draw a doodle!"
    }
    val text: LiveData<String> = _text

    /**
     * Update achievements of doodle
     * @param amount Number of doodles saved
     */
    fun updateAchievements(amount: Int) {
        val achievementRepository = AchievementRepository(app)
        achievementRepository.checkAndUpdateAchievements(AchievementType.GOAL, amount)
    }
}