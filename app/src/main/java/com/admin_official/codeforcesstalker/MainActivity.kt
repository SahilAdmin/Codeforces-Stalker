package com.admin_official.codeforcesstalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.admin_official.codeforcesstalker.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

private const val TAG = "De_MainActivity"

class MainActivity : AppCompatActivity(){

    private val viewModel by viewModels<AppViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var handlesReady = false
    private var contestsReady = false

//    val navController = this.findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.splashScreenTheme2)

        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel.loadContests()
        viewModel.usernames.observe(this, {
            viewModel.loadHandles(it)
        })

        viewModel.handles.observe(this, {
            if(it != null) {
                handlesReady = true
            }
        })

        viewModel.loadContests()

        viewModel.contests.observe(this, {
            if(it != null) {
                contestsReady = true
            }
        })

        binding.root.viewTreeObserver.addOnPreDrawListener (
            object: ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if(handlesReady && contestsReady) {
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            })

        setTheme(R.style.Theme_CodeforcesStalker)

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
        viewModel.addHandle("mexomerf")
        viewModel.addHandle("_Tian_")
//        viewModel.addHandle("Aaryan_01")
    }
}