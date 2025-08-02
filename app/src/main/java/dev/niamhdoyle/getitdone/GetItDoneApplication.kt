package dev.niamhdoyle.getitdone

import android.app.Application
import dev.niamhdoyle.getitdone.data.GetItDoneDb
import dev.niamhdoyle.getitdone.data.TaskDao

class GetItDoneApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        db = GetItDoneDb.getDb(this)
        taskDao = db.getTaskDao()
    }


    companion object {

        lateinit var db: GetItDoneDb
        lateinit var taskDao: TaskDao
    }
}