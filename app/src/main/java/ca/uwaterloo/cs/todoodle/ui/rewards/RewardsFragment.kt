package ca.uwaterloo.cs.todoodle.ui.rewards

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.data.model.Reward
import ca.uwaterloo.cs.todoodle.databinding.FragmentRewardsBinding


class RewardsFragment : Fragment() {
    private lateinit var binding: FragmentRewardsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRewardsBinding.inflate(layoutInflater)

        // Initialize rewards ViewModel
        val rewardsViewModel =
            ViewModelProvider(
                this,
                RewardsViewModelFactory(activity!!.application, "rewards.json")
            )[RewardsViewModel::class.java]

        // Get updated rewards info from local asset and render the page
        val rewardsObservable = rewardsViewModel.loadRewards()
        rewardsObservable.observe(activity!!) { rewards ->
            initRewardsLayout(rewards)
        }

        return binding.root
    }

    /**
     * Programmatically create the layout. This is a trade-off between configurability and performance
     * @param rewards Reward list
     */
    private fun initRewardsLayout(rewards: List<Reward>) {
        // Must create a LinearLayout inside ScrollView
        val wrapper = LinearLayout(activity!!)
        wrapper.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        wrapper.layoutParams = layoutParams

        // For row container
        val layoutParamsRow = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0F,
        )
        layoutParamsRow.setMargins(0, 16, 0, 16)

        // For col container
        val layoutParamsCol = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0F,
        )

        // For icon content
        val layoutParamsIcon = LinearLayout.LayoutParams(
            320,
            320
        )

        // For button content
        val layoutParamsBtn = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        layoutParamsBtn.marginStart = 64
        layoutParamsBtn.marginEnd = 64

        for (rewardRow in rewards.chunked(2)) {
            // Reward row
            val row = LinearLayout(activity!!)
            row.orientation = LinearLayout.HORIZONTAL
            row.layoutParams = layoutParamsRow

            for (reward in rewardRow) {
                // Reward column
                val col = LinearLayout(activity!!)
                col.orientation = LinearLayout.VERTICAL
                col.gravity = Gravity.CENTER
                col.layoutParams = layoutParamsCol

                // Image
                val icon = ImageView(activity!!)
                val resID =
                    resources.getIdentifier(reward.id, "drawable", activity!!.packageName)
                icon.setImageResource(resID)
                icon.layoutParams = layoutParamsIcon
                col.addView(icon)

                // Caption
                val title = TextView(activity!!)
                title.text = reward.title
                title.gravity = Gravity.CENTER_HORIZONTAL
                title.textSize = 20F
                title.typeface = Typeface.DEFAULT_BOLD
                col.addView(title)

                // Points
                val desc = TextView(activity!!)
                desc.text = reward.points.toString() + " AP"
                desc.gravity = Gravity.CENTER_HORIZONTAL
                desc.textSize = 18F
                col.addView(desc)

                // Button
                val btn =
                    LayoutInflater.from(activity)
                        .inflate(ca.uwaterloo.cs.todoodle.R.layout.default_button, null) as Button
                btn.text = "Redeem"
                btn.layoutParams = layoutParamsBtn
                addRedeemEvent(btn, reward.id)
                col.addView(btn)

                // Fill row
                row.addView(col)
            }

            // Append row
            wrapper.addView(row)

        }

        // Append wrapper to scrollview
        binding.rewardsWrapper.addView(wrapper)
    }

    /**
     * Attach click event listener to each button
     * @param rewardID Reward to be redeemed
     */
    private fun addRedeemEvent(btn: Button, rewardID: String) {
        btn.setOnClickListener {
            println(rewardID)
        }
    }

}
