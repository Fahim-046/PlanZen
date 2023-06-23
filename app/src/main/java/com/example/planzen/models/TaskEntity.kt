package com.example.planzen.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val task: String,
    var taskStatus: Boolean
)
