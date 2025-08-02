package dev.niamhdoyle.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.Task
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    val taskDao = GetItDoneApplication.taskDao

    suspend fun fetchTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}