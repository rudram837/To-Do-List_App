package com.example.to_dolistapp

// TaskViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    fun addTask(task: Task) {
        _tasks.value?.add(task)
        _tasks.value = _tasks.value
    }

    fun updateTask(position: Int, newTask: Task) {
        _tasks.value?.set(position, newTask)
        _tasks.value = _tasks.value
    }

    fun deleteTask(position: Int) {
        _tasks.value?.removeAt(position)
        _tasks.value = _tasks.value
    }
}
