package ca.uwaterloo.cs.todoodle.data.model

/**
 * Achievement Interface. Keep consistent with asset JSON
 */
data class Achievement(
    val imageURI: String,
    val id: String,
    val title: String,
    val desc: String,
    val points: Int,
    var done: Boolean = false,
)

/**
 * Type of achievement. Pass this to achievement updater during DB manipulation
 * @param id Partial of the achievement id defined in the JSON file
 */
enum class AchievementType(val id: String){
    TASK("series_task"),
    GOAL("series_goal"),
    DUE("series_task_due"),
    CALENDAR("single_calendar"),
    PARENT("single_parent"),
    CHILD("single_child"),
    PROFILE("single_makeover"),
    ACHIEVEMENT("series_acvm"),
}