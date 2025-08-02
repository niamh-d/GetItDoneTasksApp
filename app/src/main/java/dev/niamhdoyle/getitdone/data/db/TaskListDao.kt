package dev.niamhdoyle.getitdone.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.niamhdoyle.getitdone.data.model.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDao {

    @Insert
    suspend fun createTaskList(list: TaskList)

    @Query("SELECT * FROM task_list")
    fun getAllLists(): Flow<List<TaskList>>

    @Update
    suspend fun updateTaskList(list: TaskList)

    @Delete
    suspend fun deleteTaskList(list: TaskList)
}