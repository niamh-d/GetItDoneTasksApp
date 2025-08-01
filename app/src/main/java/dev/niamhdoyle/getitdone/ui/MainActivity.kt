package dev.niamhdoyle.getitdone.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dev.niamhdoyle.getitdone.data.GetItDoneDb
import dev.niamhdoyle.getitdone.databinding.ActivityMainBinding
import dev.niamhdoyle.getitdone.databinding.DialogAddNewTaskBinding
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.data.TaskDao
import dev.niamhdoyle.getitdone.ui.tasks.TasksFragment
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private val db: GetItDoneDb by lazy { GetItDoneDb.getDb(this) }
    private val taskDao: TaskDao by lazy { db.getTaskDao() }
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater).apply {
            pager.adapter = PagerAdapter(this@MainActivity)
            TabLayoutMediator(tabs, pager) { tab, _ ->
                tab.text = "Tasks"
            }.attach()

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
                btnSave.isEnabled = !input.isNullOrEmpty()
            }

            btnShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            btnSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                )
                thread {
                    taskDao.createTask(task)
                }
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }
    }
}