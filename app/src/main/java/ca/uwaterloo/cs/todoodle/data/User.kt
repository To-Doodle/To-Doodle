package ca.uwaterloo.cs.todoodle.data

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "completed_achievements") @TypeConverters(AchievementConverter::class) val completedAchievements: HashMap<String, Int>?
)