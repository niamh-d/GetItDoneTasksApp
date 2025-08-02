package dev.niamhdoyle.getitdone.ui.tasks

import androidx.lifecycle.ViewModel
import dev.niamhdoyle.getitdone.GetItDoneApplication
import dev.niamhdoyle.getitdone.data.Task
import kotlin.concurrent.thread

class TasksViewModel : ViewModel() {

    val taskDao = GetItDoneApplication.taskDao

    fun fetchTasks(cb: (List<Task>) -> Unit) {
        thread {
            val tasks = taskDao.getAllTasks()
            cb(tasks)
        }
    }

    fun updateTask(task: Task) {
        thread {
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        thread {
            taskDao.deleteTask(task)
        }
    }
}