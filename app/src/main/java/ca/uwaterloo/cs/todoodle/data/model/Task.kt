package ca.uwaterloo.cs.todoodle.data.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

enum class TaskType(val id: String){
    IN_PROGRESS("in_progress"),
    PENDING_APPROVAL("pending_approval"),
    COMPLETED("completed")
}

@IgnoreExtraProperties
data class Task(
    val taskName: String? = null,
    val deadline: String? = null,
    val category: String? = null,
    val duration: String? = null,
    val level: String? = null,
    val notes: String? = null,
    val status: TaskType? = null
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}