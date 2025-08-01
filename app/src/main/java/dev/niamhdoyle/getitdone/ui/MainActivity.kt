package dev.niamhdoyle.getitdone.ui

import android.os.Bundle
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
import dev.niamhdoyle.getitdone.data.Task
import dev.niamhdoyle.getitdone.ui.tasks.TasksFragment
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private lateinit var db: GetItDoneDb
    private val taskDao by lazy { db.getTaskDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(vb.tabs, vb.pager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        vb.fab.setOnClickListener { showAddNewTaskDialog() }

        db = GetItDoneDb.createDb(this)

    }

    private fun showAddNewTaskDialog() {
        val dialogBinding = DialogAddNewTaskBinding.inflate(layoutInflater)

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnShowDetails.setOnClickListener {
            dialogBinding.editTextTaskDetails.visibility =
                if (dialogBinding.editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
        }

        dialogBinding.btnSave.setOnClickListener {
            val task = Task(
                title = dialogBinding.editTextTaskTitle.text.toString(),
                description = dialogBinding.editTextTaskDetails.text.toString()
            )
            thread {
                taskDao.createTask(task)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }
}