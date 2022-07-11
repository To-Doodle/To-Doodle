package ca.uwaterloo.cs.todoodle.data.model

/**
 * Achievement Interface. Keep consistent with asset JSON
 */
data class Achievement(
    val imageURI: String,
    val id: String,
    val title: String,
<<<<<<< HEAD
    val desc: String,
    var done: Boolean = false,
=======
    val desc: String
>>>>>>> Apply MVVM to achievements
)