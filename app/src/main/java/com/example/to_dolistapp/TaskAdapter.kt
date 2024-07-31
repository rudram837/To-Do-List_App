package com.example.to_dolistapp

// TaskAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolistapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val tasks: List<Task>,
    private val onEditClick: (Task, Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {
            binding.taskTitle.text = task.title
            binding.checkbox.isChecked = task.isCompleted

            binding.editButton.setOnClickListener { onEditClick(task, position) }
            binding.deleteButton.setOnClickListener { onDeleteClick(position) }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], position)
    }

    override fun getItemCount() = tasks.size
}
