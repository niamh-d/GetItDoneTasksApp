package dev.niamhdoyle.getitdone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.Task
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val taskDao = GetItDoneApplication.taskDao

    fun createTask(title: String, description: String?) {
        viewModelScope.launch {
            taskDao.createTask(
                Task(
                    title = title,
                    description = description
                )
            )
        }
    }
}