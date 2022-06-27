package ca.uwaterloo.cs.todoodle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import ca.uwaterloo.cs.todoodle.data.LoginRepository
import ca.uwaterloo.cs.todoodle.data.Result

import ca.uwaterloo.cs.todoodle.R
import ca.uwaterloo.cs.todoodle.data.Task
import ca.uwaterloo.cs.todoodle.data.TaskRepository
import kotlinx.coroutines.flow.Flow

class ToDoViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val getAllTask = taskRepository.getAllTasks()

    fun addTask(task: Task) {
        taskRepository.addTask(task)
    }

    fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: Task) {
        taskRepository.deleteTask(task)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return getAllTask
    }

}