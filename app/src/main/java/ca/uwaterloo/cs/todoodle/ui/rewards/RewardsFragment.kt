package ca.uwaterloo.cs.todoodle.ui.rewards

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.R
import ca.uwaterloo.cs.todoodle.data.model.Achievement
import ca.uwaterloo.cs.todoodle.databinding.FragmentAchievementsBinding
import ca.uwaterloo.cs.todoodle.databinding.FragmentRewardsBinding
import ca.uwaterloo.cs.todoodle.ui.achievements.AchievementsViewModel
import ca.uwaterloo.cs.todoodle.ui.achievements.AchievementsViewModelFactory

class RewardsFragment : Fragment() {
    private lateinit var binding: FragmentRewardsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRewardsBinding.inflate(layoutInflater)

        return binding.root
    }

}
