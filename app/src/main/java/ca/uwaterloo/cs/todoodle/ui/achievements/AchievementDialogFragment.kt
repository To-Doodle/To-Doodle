package ca.uwaterloo.cs.todoodle.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ca.uwaterloo.cs.todoodle.data.AchievementRepository
import ca.uwaterloo.cs.todoodle.databinding.AchievementDialogBinding
import ca.uwaterloo.cs.todoodle.databinding.RewardDialogBinding

class AchievementDialogFragment(private val achievementID: String) : DialogFragment() {
    private lateinit var binding: AchievementDialogBinding
    private lateinit var achievementRepository: AchievementRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AchievementDialogBinding.inflate(layoutInflater)
        achievementRepository = AchievementRepository(activity!!.application, "achievements.json")

        // Apply the layout
        initAchievementBody()

        return binding.root

    }

    /**
     * Initialize achievement widget in the dialog
     * @param achievementID Achievement ID
     */
    private fun initAchievementBody() {
        val achievement = achievementRepository.achievements.find {
            it.id == achievementID
        } ?: return

        val title = binding.achievementBodyTitle
        title.text = achievement.title

        val desc = binding.achievementBodyDesc
        desc.text = achievement.desc

        val points = binding.achievementBodyPoints
        points.text = "+ " + achievement.points.toString() + " AP"
    }
}