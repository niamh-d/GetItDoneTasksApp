package dev.niamhdoyle.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.databinding.FragmentTasksBinding

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
        viewModel.fetchTasks { tasks ->
            requireActivity().runOnUiThread {
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