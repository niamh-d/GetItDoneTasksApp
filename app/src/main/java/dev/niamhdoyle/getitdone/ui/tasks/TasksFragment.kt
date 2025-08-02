package dev.niamhdoyle.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dev.niamhdoyle.getitdone.data.model.Task
import dev.niamhdoyle.getitdone.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment() : Fragment(), TasksAdapter.TaskItemClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var vb: FragmentTasksBinding
    private val adapter = TasksAdapter(this)
    private var listId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentTasksBinding.inflate(inflater, container, false)
        listId = requireArguments().getInt("listId")
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.recyclerView.adapter = adapter
        fetchAllTasks()
    }

    private fun fetchAllTasks() {
        lifecycleScope.launch {
            viewModel.fetchTasks(listId ?: 0).collectLatest { tasks ->
                adapter.setTasks(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
    }
}