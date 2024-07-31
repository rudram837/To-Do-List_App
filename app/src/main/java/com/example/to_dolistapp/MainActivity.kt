package com.example.to_dolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// MainActivity.kt
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_dolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskAdapter = TaskAdapter(taskViewModel.tasks.value ?: listOf(), this::onEditTask, this::onDeleteTask)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        taskViewModel.tasks.observe(this) {
            taskAdapter.notifyDataSetChanged()
        }

        binding.addButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_task, null)
        val taskTitleEditText = dialogView.findViewById<EditText>(R.id.taskTitleEditText)

        AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)

            .setPositiveButton("Add") { _, _ ->
                val taskTitle = taskTitleEditText.text.toString()
                if (taskTitle.isNotEmpty()) {
                    val task = Task(taskTitle)
                    taskViewModel.addTask(task)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun onEditTask(task: Task, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_task, null)
        val taskTitleEditText = dialogView.findViewById<EditText>(R.id.taskTitleEditText)
        taskTitleEditText.setText(task.title)

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newTaskTitle = taskTitleEditText.text.toString()
                if (newTaskTitle.isNotEmpty()) {
                    val updatedTask = task.copy(title = newTaskTitle)
                    taskViewModel.updateTask(position, updatedTask)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun onDeleteTask(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Delete") { _, _ ->
                taskViewModel.deleteTask(position)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}
