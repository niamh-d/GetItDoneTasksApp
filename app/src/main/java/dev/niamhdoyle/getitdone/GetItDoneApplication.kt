package dev.niamhdoyle.getitdone

import android.app.Application
import dev.niamhdoyle.getitdone.data.TaskRepository
import dev.niamhdoyle.getitdone.data.db.GetItDoneDb
import dev.niamhdoyle.getitdone.data.db.TaskDao
import dev.niamhdoyle.getitdone.data.db.TaskListDao

class GetItDoneApplication : Application() {

    private lateinit var db: GetItDoneDb
    private lateinit var taskDao: TaskDao
    private lateinit var taskListDao: TaskListDao

    override fun onCreate() {
        super.onCreate()
        db = GetItDoneDb.getDb(this)
        taskDao = db.getTaskDao()
        taskListDao = db.getTaskListDao()
        taskRepository = TaskRepository(taskDao, taskListDao)
    }


    companion object {
        lateinit var taskRepository: TaskRepository
    }
}