package dev.niamhdoyle.getitdone

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDb: RoomDatabase() {

}