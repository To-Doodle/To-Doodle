package ca.uwaterloo.cs.todoodle.data

import androidx.room.*
import ca.uwaterloo.cs.todoodle.data.model.Achievement

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE uid = :userId")
    fun findById(userId: Int): User

    @Query("UPDATE user SET completed_achievements = :achievements WHERE uid = :userId")
    fun updateCompletedAchievements(userId: Int, achievements: HashMap<String, Int>)
}