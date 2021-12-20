package com.admin_official.codeforcesstalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.admin_official.codeforcesstalker.data.Username
import com.admin_official.codeforcesstalker.data.UsernameDao
import com.admin_official.codeforcesstalker.data.UsernameDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "De_MainActivity"

class MainActivity : AppCompatActivity(){

    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.addHandle("smahajan54842")

        viewModel.usernames.observe(this, {
            if(it != null) {
                viewModel.loadHandles(it)
                viewModel.loadStandings(it, 1617)
            } else Log.d(TAG, "onCreate: no usernames found")
        })
        
        viewModel.handles.observe(this, {
            Log.d(TAG, "onCreate: $it")
        })
        
        viewModel.loadContests()
        
        viewModel.contests.observe(this, {
//            Log.d(TAG, "onCreate: contests $it")
        })
        
        viewModel.standings.observe(this, {
            Log.d(TAG, "onCreate: $it")
        })
    }
}