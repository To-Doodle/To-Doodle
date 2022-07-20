package ca.uwaterloo.cs.todoodle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.data.model.Achievement

class AchievementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        title = "Achievements"

        // Initialize achievements ViewModel
        val achievementsViewModel =
            ViewModelProvider(
                this,
                AchievementsViewModelFactory(application, "achievements.json")
            )[AchievementsViewModel::class.java]

        // Get updated achievements info from local asset and DB and render the page
        val observable = achievementsViewModel.loadAchievements()
        observable.observe(this) { achievements ->
            val updatedAchievements =
                achievementsViewModel.updateAchievementCompletionStatus(achievements)
            initAchievementsLayout(updatedAchievements)
        }

    }

    /**
     * Programmatically create the layout. This is a trade-off between configurability and performance
     * @param achievements Achievements list
     */
    private fun initAchievementsLayout(achievements: List<Achievement>) {
        // Must create a LinearLayout inside ScrollView
        val wrapper = LinearLayout(this)
        wrapper.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginStart = 32
        layoutParams.marginEnd = 32
        wrapper.layoutParams = layoutParams

        // For row container
        val layoutParamsRow = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
        )
        layoutParamsRow.setMargins(0, 16, 0, 16)

        // For icon content
        val layoutParamsIcon = LinearLayout.LayoutParams(
            256,
            320
        )

        // For body content
        val layoutParamsBody = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )
        layoutParamsBody.marginStart = -128

        // For divider
        val layoutParamsDivider = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            1,
        )

        for (achievement in achievements) {
            // Achievement wrapper
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL
            row.gravity = Gravity.CENTER
            row.layoutParams = layoutParamsRow

            // Points Border
            val border = ImageView(this)
            val borderResID = resources.getIdentifier("points_border", "drawable", packageName)
            border.setImageResource(borderResID)
            border.layoutParams = layoutParamsIcon
            row.addView(border)

            // Points
            val icon = ImageView(this)
            val resID = resources.getIdentifier(achievement.imageURI, "drawable", packageName)
            icon.setImageResource(resID)
            icon.translationX = -208F
            row.addView(icon)

            // Title & description
            val body = LinearLayout(this)
            body.orientation = LinearLayout.VERTICAL
            body.layoutParams = layoutParamsBody
            body.gravity = Gravity.CENTER
            body.setBackgroundResource(R.drawable.points_title)

            val title = TextView(this)
            title.text = achievement.title
            title.gravity = Gravity.CENTER_HORIZONTAL
            title.textSize = 24F
            title.translationY = -32F

            val desc = TextView(this)
            desc.text = achievement.desc
            desc.gravity = Gravity.CENTER_HORIZONTAL
            desc.textSize = 18F
            desc.translationY = 64F
            desc.maxLines = 1

            // Fill body
            body.addView(title)
            body.addView(desc)

            // Fill row
            row.addView(body)

            // Append row
            wrapper.addView(row)

            val divider = View(this)
            divider.layoutParams = layoutParamsDivider
            divider.setBackgroundColor(Color.GRAY)
            wrapper.addView(divider)

        }

        // Append wrapper to scrollview
        findViewById<ScrollView>(R.id.achievementsWrapper).addView(wrapper)
    }


}
