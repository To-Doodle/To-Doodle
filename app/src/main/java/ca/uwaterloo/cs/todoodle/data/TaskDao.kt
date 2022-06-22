package ca.uwaterloo.cs.todoodle.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id")
    fun getAllTasks(): Flow<List<Task>>

//    @Query("SELECT * FROM tasks WHERE id IN (:id)")
//    fun loadTaskById(id: Int): Flow<List<Task>>

    @Insert
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}