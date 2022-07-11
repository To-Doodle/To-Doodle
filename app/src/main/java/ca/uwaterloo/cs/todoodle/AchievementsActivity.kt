package ca.uwaterloo.cs.todoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import ca.uwaterloo.cs.todoodle.data.model.Achievement

class AchievementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        setContentView(R.layout.activity_achievements)
        title = "Achievements"

        // Initialize achievements ViewModel
=======
        setContentView(R.layout.activity_rewards)
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
        achievementsViewModel.loadAchievements().observe(this) { achievements ->
            val updatedAchievements =
                achievementsViewModel.updateAchievementCompletionStatus(achievements)
            initAchievementsLayout(updatedAchievements)
>>>>>>> Add achievement status handler
        }

    }

    /**
     * Programmatically create the layout. This is a trade-off between configurability and performance
     */
    private fun initAchievementsLayout(achievements: List<Achievement>) {
        // Must create a LinearLayout inside ScrollView
        val wrapper = LinearLayout(this)
        wrapper.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginStart = 16
        layoutParams.marginEnd = 16
        wrapper.layoutParams = layoutParams

        for (achievement in achievements) {
            // Achievement wrapper
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            // Image
            val image = ImageView(this)
            val resID = resources.getIdentifier(achievement.imageURI, "drawable", packageName)
            image.setImageResource(resID)
            row.addView(image)

            // Title & description
            val body = LinearLayout(this)
            body.orientation = LinearLayout.VERTICAL
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            body.layoutParams = layoutParams

            val title = TextView(this)
            title.text = achievement.title
            title.gravity = Gravity.CENTER
            title.textSize = 20F
            title.translationY = 20F

            val desc = TextView(this)
            desc.text = achievement.desc
            desc.gravity = Gravity.CENTER
            desc.translationY = 30F

            body.addView(title)
            body.addView(desc)
            row.addView(body)

            /**
             * May add AP
             */

            wrapper.addView(row)
        }

        // Append wrapper to scrollview
        findViewById<ScrollView>(R.id.achievementsWrapper).addView(wrapper)
    }


}
