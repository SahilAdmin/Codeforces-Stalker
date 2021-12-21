package com.admin_official.codeforcesstalker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fm: FragmentManager, private val lifeCycle: Lifecycle):
    FragmentStateAdapter(fm, lifeCycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> FragmentContests.newInstance()

            else -> FragmentUserInfo.newInstance()
        }
    }
}