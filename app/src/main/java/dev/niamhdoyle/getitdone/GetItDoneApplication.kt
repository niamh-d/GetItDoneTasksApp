package dev.niamhdoyle.getitdone

import android.app.Application
import dev.niamhdoyle.getitdone.data.TaskRepository
import dev.niamhdoyle.getitdone.data.db.GetItDoneDb
import dev.niamhdoyle.getitdone.data.db.TaskDao

class GetItDoneApplication : Application() {

    private lateinit var db: GetItDoneDb
    private lateinit var taskDao: TaskDao

    override fun onCreate() {
        super.onCreate()
        db = GetItDoneDb.getDb(this)
        taskDao = db.getTaskDao()
        taskRepository = TaskRepository(taskDao)
    }


    companion object {
        lateinit var taskRepository: TaskRepository
    }
}