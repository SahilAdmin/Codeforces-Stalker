package com.admin_official.codeforcesstalker.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.admin_official.codeforcesstalker.FragmentContests
import com.admin_official.codeforcesstalker.FragmentUserInfo

class ViewPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle):
    FragmentStateAdapter(fm, lifeCycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> FragmentContests.newInstance()

            else -> FragmentUserInfo.newInstance()
        }
    }
}