package ca.uwaterloo.cs.todoodle.data.model

/**
 * Reward Interface. Keep consistent with asset JSON
 */
data class Reward(
    val id: String,
    val title: String,
    val points: Int,
)