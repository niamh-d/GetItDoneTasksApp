package dev.niamhdoyle.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks.sortedBy { it.isCompleted }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                checkbox.isChecked = task.isCompleted
                toggleStar.isChecked = task.isStarred
                if (task.isCompleted) {
                    textViewTaskTitle.paintFlags =
                        textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewTaskDescription.paintFlags =
                        textViewTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTaskTitle.paintFlags = 0
                    textViewTaskDescription.paintFlags = 0
                }
                textViewTaskTitle.text = task.title
                if (task.description.isNullOrEmpty()) {
                    textViewTaskDescription.visibility = View.GONE
                } else {
                    textViewTaskDescription.text = task.description
                    textViewTaskDescription.visibility = View.VISIBLE
                }

                checkbox.setOnClickListener {
                    listener.onTaskUpdated(task.copy(isCompleted = checkbox.isChecked))
                }
                toggleStar.setOnClickListener {
                    listener.onTaskUpdated(task.copy(isStarred = toggleStar.isChecked))
                }
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}