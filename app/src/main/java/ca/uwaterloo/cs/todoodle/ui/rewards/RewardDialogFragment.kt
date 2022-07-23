package ca.uwaterloo.cs.todoodle.ui.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ca.uwaterloo.cs.todoodle.databinding.RewardDialogBinding

class RewardDialogFragment : DialogFragment() {
    private lateinit var binding: RewardDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RewardDialogBinding.inflate(layoutInflater)

        // Apply the code
        binding.rewardCode.text = generateCode()

        return binding.root

    }

    /**
     * Generate a random 4-digit code
     * @return 4-digit code
     */
    private fun generateCode(): String {
        return (0..9).shuffled().take(4).joinToString("")
    }
}