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
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dev.niamhdoyle.getitdone.R
import dev.niamhdoyle.getitdone.databinding.ActivityMainBinding
import dev.niamhdoyle.getitdone.databinding.DialogAddNewTaskBinding
import dev.niamhdoyle.getitdone.ui.tasks.TasksFragment
import dev.niamhdoyle.getitdone.util.InputValidator


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater).apply {
            pager.adapter = PagerAdapter(this@MainActivity)
            pager.currentItem = 1
            TabLayoutMediator(tabs, pager) { tab, position ->
                when (position) {
                    0 -> tab.icon =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_star_filled_24)

                    1 -> tab.text = "Tasks"
                    2 -> tab.customView = Button(this@MainActivity).apply {
                        text = "Add new list"
                    }
                }
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
                btnSave.isEnabled = InputValidator.isInputValid(input.toString())
            }

            btnShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
            }

            btnSave.setOnClickListener {
                viewModel.createTask(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                )
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }
}