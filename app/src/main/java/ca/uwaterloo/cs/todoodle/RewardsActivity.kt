package ca.uwaterloo.cs.todoodle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class RewardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)
        title = "Achievements"

        initAchievementsLayout()

    }

    /**
     * Programmatically create the layout. This is a trade-off between configurability and performance
     */
    private fun initAchievementsLayout() {
        val achievements = parseAchievementJSON("achievements.json")

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

    /**
     * Load achievement data form local asset
     */
    private fun parseAchievementJSON(filename: String): List<Achievement> {
        // Read asset file
        val jsonString: String
        try {
            jsonString = assets.open(filename).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return listOf()
        }

        // Parse JSON string
        val gson = Gson()
        val listAchievementType = object : TypeToken<List<Achievement>>() {}.type

        return gson.fromJson(jsonString, listAchievementType)
    }
}

/**
 * Achievement Interface. Keep consistent with asset JSON
 */
data class Achievement(
    val imageURI: String,
    val id: String,
    val title: String,
    val desc: String
) {}
