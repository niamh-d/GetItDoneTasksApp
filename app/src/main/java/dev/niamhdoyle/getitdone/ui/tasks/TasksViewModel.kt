package dev.niamhdoyle.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.TaskRepository
import dev.niamhdoyle.getitdone.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {

    private val repository: TaskRepository = GetItDoneApplication.taskRepository

    fun fetchTasks(taskListId: Int): Flow<List<Task>> {
        return repository.getTasks(taskListId)
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}