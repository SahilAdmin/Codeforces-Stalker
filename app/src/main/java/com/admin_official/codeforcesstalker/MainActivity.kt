package com.admin_official.codeforcesstalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.admin_official.codeforcesstalker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

private const val TAG = "De_MainActivity"

class MainActivity : AppCompatActivity(){

    private val viewModel by viewModels<AppViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        val pager = binding.pager.apply {adapter = fragmentAdapter}
        val tabs = binding.tabs

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabs.selectTab(tabs.getTabAt(position))
            }
        })

        viewModel.addHandle("smahajan54842")
        viewModel.addHandle("ashutosh.2805")
        viewModel.addHandle("papapyjama")
    }
}