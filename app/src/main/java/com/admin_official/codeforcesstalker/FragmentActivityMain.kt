package com.admin_official.codeforcesstalker


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.admin_official.codeforcesstalker.adapters.ViewPagerAdapter
import com.admin_official.codeforcesstalker.databinding.FragmentActivityMainBinding
import com.google.android.material.tabs.TabLayout

private const val TAG = "De_FragmentActivityMain"
class FragmentActivityMain : Fragment() {

    private lateinit var binding: FragmentActivityMainBinding

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivityMainBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setTheme(R.style.Theme_CodeforcesStalker)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        Log.d(TAG, "onCreateOptionsMenu: in")
        inflater.inflate(R.menu.userinfo_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add -> {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentActivityMain()
    }
}