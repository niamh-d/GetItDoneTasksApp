package dev.niamhdoyle.getitdone.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dev.niamhdoyle.getitdone.R
import dev.niamhdoyle.getitdone.data.model.TaskList
import dev.niamhdoyle.getitdone.databinding.ActivityMainBinding
import dev.niamhdoyle.getitdone.databinding.DialogAddNewTaskBinding
import dev.niamhdoyle.getitdone.ui.tasks.StarredTasksFragment
import dev.niamhdoyle.getitdone.ui.tasks.TasksFragment
import dev.niamhdoyle.getitdone.util.InputValidator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var vb: ActivityMainBinding
    private var currentTaskLists: List<TaskList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleScope.launch {
                viewModel.getTaskLists().collectLatest { taskLists ->

                    currentTaskLists = taskLists

                    pager.adapter = PagerAdapter(this@MainActivity, taskLists.size + 2)
                    pager.currentItem = 1
                    TabLayoutMediator(tabs, pager) { tab, position ->
                        when (position) {
                            0 -> tab.icon =
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.ic_star_filled_24
                                )

                            taskLists.size + 1 -> tab.customView = Button(this@MainActivity).apply {
                                text = "Add new list"
                            }

                            else -> {
                                tab.text = taskLists[position - 1].name
                            }
                        }
                    }.attach()
                }
            }

            fab.setOnClickListener { showAddNewTaskDialog() }
            setContentView(root)
        }
    }

    private fun showAddNewTaskDialog() {
        DialogAddNewTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            btnSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener { input ->
                btnSave.isEnabled = InputValidator.isInputValid(input.toString())
            }

            btnShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            btnSave.setOnClickListener {
                val listId = currentTaskLists[vb.pager.currentItem - 1].id
                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString(),
                    listId = listId
                )
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity, private val numPages: Int) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = numPages

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> StarredTasksFragment()
                else -> TasksFragment()
            }
        }
    }
}