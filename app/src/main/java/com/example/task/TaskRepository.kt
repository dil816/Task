package com.example.task

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import java.util.concurrent.Flow

class TaskRepository(private val tasksDao: TasksDao) {

    val allTasks: LiveData<List<Task>> = tasksDao.getAlltasks()

    suspend fun insert(task: Task) {
        tasksDao.insert(task)
    }

    suspend fun delete(task: Task) {
        tasksDao.delete(task)
    }

    suspend fun update(task: Task) {
        tasksDao.update(task)
    }
}
