package com.example.planzen.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.planzen.models.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity): Long

    @Query("SELECT * FROM tasks")
    suspend fun allTasks(): List<TaskEntity>

    @Query("DELETE FROM tasks WHERE id=:id")
    suspend fun deleteTask(id: Int)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id=:id")
    suspend fun getTaskById(id: Int): TaskEntity?
}
