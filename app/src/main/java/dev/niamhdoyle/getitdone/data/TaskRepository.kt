package dev.niamhdoyle.getitdone.data

import dev.niamhdoyle.getitdone.data.db.TaskDao
import dev.niamhdoyle.getitdone.data.db.TaskListDao
import dev.niamhdoyle.getitdone.data.model.Task
import dev.niamhdoyle.getitdone.data.model.TaskList
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val taskListDao: TaskListDao) {

    suspend fun createTask(task: Task) {
        taskDao.createTask(task)
    }

    fun getTasks(taskListId: Int): Flow<List<Task>> {
        return taskDao.getAllTasks(taskListId)
    }

    fun getStarredTasks(): Flow<List<Task>> {
        return taskDao.getStarredTasks()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getTaskLists(): Flow<List<TaskList>> {
        return taskListDao.getAllLists()
    }

    suspend fun createTaskList(listName: String) {
        taskListDao.createTaskList(TaskList(name = listName))
    }
}