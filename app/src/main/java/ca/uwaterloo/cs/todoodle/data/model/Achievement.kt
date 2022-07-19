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
    val pointsURI: String,
    var done: Boolean = false,
)