package dev.niamhdoyle.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDb : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        fun createDb(context: Context): GetItDoneDb {
            return Room.databaseBuilder(
                context,
                GetItDoneDb::class.java,
                "get-it-done-db"
            ).build()
        }
    }
}