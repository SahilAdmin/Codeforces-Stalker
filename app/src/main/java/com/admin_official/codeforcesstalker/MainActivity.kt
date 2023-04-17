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

//        viewModel.authenticate.value = false

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

//        viewModel.addHandle2("ashutosh.2805")
//        viewModel.addHandle2("papapyjama")
//        viewModel.addHandle2("mexomerf")
//        viewModel.addHandle2("_Tian_")
//        viewModel.addHandle("Aaryan_01")
    }


    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.userinfo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }*/
}

/*
VIEW_MODEL ->

implementation 'androidx.activity:activity-ktx:1.5.1' // for by viewModels (for activities)
implementation 'androidx.fragment:fragment-ktx:1.5.4' // for by activityViewModels (for fragments)

To share data we can use LiveData, StateFlow, SharedFlow, Flows

LiveData is lifecycle aware, i.e. it will only observe when the activity it is observing for is in the
foreground, we have to provide context (View) for livedata

A flow on the other hand is like a coroutine which can return multiple values (of the same type)
Flows can be of 2 types hot flow and a cold flow

Hot flow / hot stream is a flow which emits data irrespective of whether there are collectors listening to it or not
Whereas a Cold flow is a flow which emits data only when there is a collector listening to it

SharedFlow and StateFlow are hot flows.

// Syntax to make a flow
val countDownFlow = flow<Int> {
    val startingValue = 10;
    var currentValue = startingValue
    emit(currentValue)

    while(currentValue > 0) {
        delay(1000L)
        currentValue--
        emit(currentValue)
    }
}

private fun initiateCountdown() = viewModelScope.launch {
    countDownFlow.collect {
        println("The value is $it")
    }
} // This function will complete as soon as the countdown completes or if the ViewModel is destroyed
  // since it is in the viewModelScope


SharedFlow and StateFlow should only called from lifeCycleScope.launchWhenStarted or from the
repeatOnLifeCycle(LifeCycle.State.STARTED) block since a flow is not lifecycle aware and if we call
it from the lifeCycleScope.launch block it will continue observing even when the activity is in the
background.

The main difference between sharedFlow and a stateFlow is that a sharedFlow is used to emit a single
event whereas a stateFlow is used to observe like a LiveData.
StateFlow requires a default value which is emitted as soon the observer is connected whereas with
SharedFlow we do not require a default value

Under the hood StateFlow is a SharedFlow which never completes.

private val _stateFlow = MutableStateFlow(1)
val stateFlow = _stateFlow.asStateFlow()

private val _sharedFlow = MutableSharedFlow<Int>(replay = 10)
val sharedFlow = _sharedFlow.asSharedFlow()

Here the replay value in the shared flow is the number of emissions we want to save so that
new observer can observe these emits. Since, if there are no observers of sharedFlow during the time
of emit then nothing will happen.

fun incrementFlow () {
    viewModelScope.launch {
        delay(2000L)
        _stateFlow.value ++
    }
}

fun incrementLiveData () {
    viewModelScope.launch {
        delay(2000L)
        _liveData.value = _liveData.value!! + 1
    }
}

private fun <T> AppCompatActivity.collectStateFlow (flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
} // extension function to shorten the code

collectStateFlow(viewModel.stateFlow) {
        Snackbar.make(
            binding.root,
            "Hi",
            Snackbar.LENGTH_LONG
        ).show()
        Log.d(TAG, "onCreate: $it")
    }
*/