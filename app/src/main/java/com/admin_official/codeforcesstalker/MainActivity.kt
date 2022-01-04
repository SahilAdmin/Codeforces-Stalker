package com.admin_official.codeforcesstalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import com.admin_official.codeforcesstalker.data.Username
import com.admin_official.codeforcesstalker.databinding.ActivityMainBinding
import com.admin_official.codeforcesstalker.logic.AppViewModel
import java.util.*

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
        viewModel.pUsernames.observe(this, {
            viewModel.loadHandles(it)
        })

        viewModel.handles.observe(this, {
            handlesReady = true
        })

        viewModel.contests.observe(this, {
            contestsReady = true
        })

        viewModel.authenticate.observe(this, {
            if(it) Toast.makeText(this, "Added user successfully!", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "User not found!", Toast.LENGTH_LONG).show()
        })

        binding.root.viewTreeObserver.addOnPreDrawListener (
            object: ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if(handlesReady) {
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            })

        setTheme(R.style.Theme_CodeforcesStalker)

        setContentView(binding.root)

        viewModel.addHandle2("ashutosh.2805")
        viewModel.addHandle2("papapyjama")
        viewModel.addHandle2("mexomerf")
        viewModel.addHandle2("_Tian_")
//        viewModel.addHandle("Aaryan_01")
    }


    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.userinfo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }*/
}