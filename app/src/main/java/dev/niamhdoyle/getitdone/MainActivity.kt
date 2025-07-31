package dev.niamhdoyle.getitdone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.niamhdoyle.getitdone.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(vb.tabs, vb.pager) { tab, position ->
            tab.text = "Tasks"
        }.attach()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }
}