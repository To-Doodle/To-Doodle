package ca.uwaterloo.cs.todoodle.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "task_name") val taskName: String?
)