package dev.niamhdoyle.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class GetItDoneDb : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDb? = null
        fun getDb(context: Context): GetItDoneDb {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GetItDoneDb::class.java,
                    "get-it-done-db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}