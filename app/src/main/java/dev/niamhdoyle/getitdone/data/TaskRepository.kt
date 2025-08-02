package dev.niamhdoyle.getitdone.data

import dev.niamhdoyle.getitdone.data.db.TaskDao
import dev.niamhdoyle.getitdone.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    suspend fun getTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}