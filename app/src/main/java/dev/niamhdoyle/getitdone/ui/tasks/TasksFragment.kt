package dev.niamhdoyle.getitdone.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    private lateinit var vb: FragmentTasksBinding

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
        vb.recyclerView.adapter = TasksAdapter(
            tasks = listOf(
                Task(title = "First task", description = "Description 1"),
                Task(title = "Second task", description = "Description 2"),
                Task(title = "Third task", description = "Description 3"),
                Task(title = "Fourth task", description = "Description 4")
            )
        )
    }
}