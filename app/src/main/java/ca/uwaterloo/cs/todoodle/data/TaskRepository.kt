package ca.uwaterloo.cs.todoodle.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class TaskRepository(val taskDao: TaskDao) {
    fun addTask(task: Task) = taskDao.addTask(task)

    fun getAllTasks() : Flow<List<Task>> = flow {
        taskDao.getAllTasks().collect {
            emit(it)
        }
    }

    fun updateTask(task: Task) = taskDao.updateTask(task)

    fun deleteTask(task: Task) = taskDao.deleteTask(task)
}