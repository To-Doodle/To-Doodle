package ca.uwaterloo.cs.todoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.GravityCompat
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
        layoutParams.marginStart = 16
        layoutParams.marginEnd = 16
        wrapper.layoutParams = layoutParams

        // For body content
        val layoutParamsBody = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1.0f
        )

        for (achievement in achievements) {
            // Achievement wrapper
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL
            row.gravity = Gravity.CENTER

            // Image
            val image = ImageView(this)
            val resID = resources.getIdentifier(achievement.imageURI, "drawable", packageName)
            image.setImageResource(resID)
            row.addView(image)

            // Title & description
            val body = LinearLayout(this)
            body.orientation = LinearLayout.VERTICAL
            body.layoutParams = layoutParamsBody
            body.gravity = Gravity.CENTER

            val title = TextView(this)
            title.text = achievement.title
            title.gravity = Gravity.CENTER_HORIZONTAL
            title.textSize = 20F
            title.translationY = -20F

            val desc = TextView(this)
            desc.text = achievement.desc
            desc.gravity = Gravity.CENTER_HORIZONTAL
            desc.translationY = -30F

            body.addView(title)
            body.addView(desc)
            row.addView(body)

            // Point
            val point = ImageView(this)
            val pointID = resources.getIdentifier(achievement.pointsURI, "drawable", packageName)
            point.setImageResource(pointID)
            row.addView(point)

            wrapper.addView(row)
        }

        // Append wrapper to scrollview
        findViewById<ScrollView>(R.id.achievementsWrapper).addView(wrapper)
    }


}
