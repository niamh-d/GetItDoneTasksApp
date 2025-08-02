package dev.niamhdoyle.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.databinding.FragmentTasksBinding
import kotlinx.coroutines.launch

class TasksFragment : Fragment(), TasksAdapter.TaskItemClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var vb: FragmentTasksBinding
    private val adapter = TasksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentTasksBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.recyclerView.adapter = adapter
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        lifecycleScope.launch {
            val tasks = viewModel.fetchTasks()
            adapter.setTasks(tasks)
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
        fetchAllTasks()
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
        fetchAllTasks()
    }
}