package ca.uwaterloo.cs.todoodle.ui.achievements

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uwaterloo.cs.todoodle.data.*
import ca.uwaterloo.cs.todoodle.data.model.Achievement

// Must use AndroidViewModel to obtain the application context
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
     * @return Achievements observable
     */
    fun loadAchievements(): LiveData<List<Achievement>> {
        return achievements
    }

    /**
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

    /**
     * Get user points
     * @return User points
     */
    fun getPoints(): Int {
        return achievementRepository.getPoints()
    }

}