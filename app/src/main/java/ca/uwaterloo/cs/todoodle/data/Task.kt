package ca.uwaterloo.cs.todoodle.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")

class Task {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0
    private val title: String? = null
    private val description: String? = null
}