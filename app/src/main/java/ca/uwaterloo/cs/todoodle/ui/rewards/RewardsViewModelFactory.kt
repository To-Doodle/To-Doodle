package ca.uwaterloo.cs.todoodle.ui.rewards

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel provider factory to instantiate RewardsViewModel.
 * Required given RewardsViewModel has a non-empty constructor
 */
class RewardsViewModelFactory(
    private val application: Application,
    private val filename: String
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return RewardsViewModel(application, filename) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}