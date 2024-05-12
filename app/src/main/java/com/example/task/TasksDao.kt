package com.example.task

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("Select * from TaskTable order by id ASC")
    fun getAlltasks(): LiveData<List<Task>>

    @Update
    suspend fun update(task: Task)

}
