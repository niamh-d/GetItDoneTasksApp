package dev.niamhdoyle.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.niamhdoyle.getitdone.data.GetItDoneDb
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.data.TaskDao
import dev.niamhdoyle.getitdone.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment(), TasksAdapter.TaskUpdatedListener {

    private lateinit var vb: FragmentTasksBinding
    private val taskDao: TaskDao by lazy {
        GetItDoneDb.getDb(requireContext()).getTaskDao()
    }

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
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        thread {
            val tasks = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
                vb.recyclerView.adapter = TasksAdapter(tasks = tasks, this)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread {
            taskDao.updateTask(task)
        }
    }
}