package com.example.planzen.screen.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planzen.models.TaskEntity
import com.example.planzen.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _taskItems: MutableLiveData<List<TaskEntity>> by lazy {
        MutableLiveData<List<TaskEntity>>()
    }

    val taskItems: LiveData<List<TaskEntity>?>
        get() = _taskItems

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

    private val _eventDeleteSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventDeleteSuccess: LiveData<Boolean>
        get() = _eventDeleteSuccess

    fun updateTask(id: Int, task: String, status: Boolean) = viewModelScope.launch {
        try {
            val response = repository.updateTask(TaskEntity(id, task, status))

            _eventSuccess.value = true
        } catch (e: Exception) {
            _eventSuccess.value = false
        }
    }

    // Load data
    fun loadTask() = viewModelScope.launch {
        try {
            val tasks = repository.allTask()
            _taskItems.value = tasks
        } catch (e: Exception) {
            _taskItems.postValue(listOf())

            e.printStackTrace()
        }
    }

    // Validity check
    fun isValid(
        task: String
    ): Boolean {
        if (task.isBlank()) return false
        return true
    }

    // Add data
    fun addTask(
        task: String,
        taskStatus: Boolean
    ) = viewModelScope.launch {
        if (!isValid(task)) {
            return@launch
        }

        try {
            repository.saveTask(TaskEntity(null, task, taskStatus))

            _eventSuccess.value = true
        } catch (e: Exception) {
        }
    }

    // Delete data
    fun deleteTask(id: Int) = viewModelScope.launch {
        try {
            repository.deleteTask(id)
            _eventDeleteSuccess.value = true
        } catch (e: Exception) {
        }
    }
}
