package dev.niamhdoyle.getitdone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.niamhdoyle.getitdone.data.model.Task
import dev.niamhdoyle.getitdone.data.model.TaskList

@Database(entities = [Task::class, TaskList::class], version = 4)
abstract class GetItDoneDb : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
    abstract fun getTaskListDao(): TaskListDao

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDb? = null
        private val MIGRATION_2_TO_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS 'task_list' 
                        (
                        'task_list_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        'name' TEXT NOT NULL
                        )
                    """.trimMargin()
                )
            }
        }

        fun getDb(context: Context): GetItDoneDb {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GetItDoneDb::class.java,
                    "get-it-done-db"
                )
                    .addMigrations(MIGRATION_2_TO_3)
                    .fallbackToDestructiveMigration(true)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("INSERT INTO task_list (name) VALUES ('Tasks')")
                        }
                    })
                    .build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}