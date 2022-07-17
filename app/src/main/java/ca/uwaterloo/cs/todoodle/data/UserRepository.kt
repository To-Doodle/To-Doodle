package ca.uwaterloo.cs.todoodle.data

class UserRepository(private val userDao: UserDao) {

    /**
     * Get user by id
     * @param userId User id
     * @return User object
     */
    fun findById(userId: Int): User = userDao.findById(userId)

    /**
     * Update the completed achievements after each DB manipulation
     * @param userId User id
     * @param achievements The new completed achievements
     */
    fun updateCompletedAchievements(userId: Int, achievements: HashMap<String, Int>) =
        userDao.updateCompletedAchievements(userId, achievements)
}