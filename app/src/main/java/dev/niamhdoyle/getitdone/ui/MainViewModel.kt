package dev.niamhdoyle.getitdone.ui

import androidx.lifecycle.ViewModel
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.Task
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    val taskDao = GetItDoneApplication.taskDao

    fun createTask(title: String, description: String?) {
        thread {
            taskDao.createTask(
                Task(
                    title = title,
                    description = description
                )
            )
        }
    }
}