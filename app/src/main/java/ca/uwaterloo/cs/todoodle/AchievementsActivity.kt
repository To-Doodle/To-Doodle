package ca.uwaterloo.cs.todoodle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.core.view.marginStart
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.data.model.Achievement

class AchievementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
<<<<<<< HEAD
        setContentView(R.layout.activity_achievements)
        title = "Achievements"

        // Initialize achievements ViewModel
=======
        setContentView(R.layout.activity_rewards)
=======
        setContentView(R.layout.activity_achievements)
>>>>>>> Totally refactor rewards to achievement
        title = "Achievements"

<<<<<<< HEAD
>>>>>>> Apply MVVM to achievements
=======
        // Initialize achievements ViewModel
>>>>>>> Add achievement status handler
        val achievementsViewModel =
            ViewModelProvider(
                this,
                AchievementsViewModelFactory(application, "achievements.json")
            )[AchievementsViewModel::class.java]
<<<<<<< HEAD
<<<<<<< HEAD

        // Get updated achievements info from local asset and DB and render the page
        val observable = achievementsViewModel.loadAchievements()
        observable.observe(this) { achievements ->
            val updatedAchievements =
                achievementsViewModel.updateAchievementCompletionStatus(achievements)
            initAchievementsLayout(updatedAchievements)
=======
        achievementsViewModel.loadAchievements().observe(this) { achievements ->
            initAchievementsLayout(achievements)
>>>>>>> Apply MVVM to achievements
=======

        // Get updated achievements info from local asset and DB and render the page
        val observable = achievementsViewModel.loadAchievements()
        observable.observe(this) { achievements ->
            val updatedAchievements =
                achievementsViewModel.updateAchievementCompletionStatus(achievements)
            initAchievementsLayout(updatedAchievements)
>>>>>>> Add achievement status handler
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

        for (achievement in achievements) {
            // Achievement wrapper
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL
            row.gravity = Gravity.CENTER

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

            val badge = ImageView(this)
            val badgeResID = resources.getIdentifier("points_title", "drawable", packageName)
            badge.setImageResource(badgeResID)
            badge.adjustViewBounds = true

            val title = TextView(this)
            title.text = achievement.title
            title.gravity = Gravity.CENTER_HORIZONTAL
            title.textSize = 32F
            title.translationY = -128F

            val desc = TextView(this)
            desc.text = achievement.desc
            desc.gravity = Gravity.CENTER_HORIZONTAL
            title.textSize = 24F
            desc.translationY = -60F

            body.addView(badge)
            body.addView(title)
            body.addView(desc)
            row.addView(body)

            wrapper.addView(row)
        }

        // Append wrapper to scrollview
        findViewById<ScrollView>(R.id.achievementsWrapper).addView(wrapper)
    }


}
