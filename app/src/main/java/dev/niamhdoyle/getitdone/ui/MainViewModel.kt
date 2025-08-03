package dev.niamhdoyle.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.TaskRepository
import dev.niamhdoyle.getitdone.data.model.Task
import dev.niamhdoyle.getitdone.data.model.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: TaskRepository = GetItDoneApplication.taskRepository

    fun getTaskLists(): Flow<List<TaskList>> = repository.getTaskLists()
    fun createTask(title: String, description: String?, listId: Int, isStarred: Boolean) {
        viewModelScope.launch {
            repository.createTask(
                Task(
                    title = title,
                    description = description,
                    listId = listId,
                    isStarred = isStarred
                )
            )
        }
    }

    fun addNewTaskList(listName: String?) {
        if (listName == null) return
        viewModelScope.launch {
            repository.createTaskList(listName)
        }
    }
}