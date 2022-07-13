package ca.uwaterloo.cs.todoodle.data

class UserRepository(private val userDao: UserDao) {

    /**
     * Get user by id
     */
    fun findById(userId: Int): User = userDao.findById(userId)
}