package com.example.planzen.repositories

import com.example.planzen.db.AppDatabase
import com.example.planzen.models.TaskEntity
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun saveTask(task: TaskEntity) {
        return withContext(Dispatchers.IO) {
            db.taskDao().addTask(task)
        }
    }

    suspend fun allTask(): List<TaskEntity> {
        return withContext(Dispatchers.IO) {
            db.taskDao().allTasks()
        }
    }

    suspend fun specificTask(id: Int): TaskEntity? {
        return withContext(Dispatchers.IO) {
            db.taskDao().getTaskById(id)
        }
    }

    suspend fun updateTask(task: TaskEntity) {
        return withContext(Dispatchers.IO) {
            db.taskDao().updateTask(task)
        }
    }

    suspend fun deleteTask(id: Int) {
        return withContext(Dispatchers.IO) {
            db.taskDao().deleteTask(id)
        }
    }
}
