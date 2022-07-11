package ca.uwaterloo.cs.todoodle

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel provider factory to instantiate AchivementsViewModel.
 * Required given AchivementsViewModel has a non-empty constructor
 */
class AchievementsViewModelFactory(
    private val application: Application,
    private val filename: String
<<<<<<< HEAD
<<<<<<< HEAD
) : ViewModelProvider.AndroidViewModelFactory(application) {
=======
) : ViewModelProvider.Factory {
>>>>>>> Apply MVVM to achievements
=======
) : ViewModelProvider.AndroidViewModelFactory(application) {
>>>>>>> Totally refactor rewards to achievement

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementsViewModel::class.java)) {
            return AchievementsViewModel(application, filename) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}