package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.adapters.SwipeGesture
import com.admin_official.codeforcesstalker.adapters.UserInfoRecyclerViewAdapter
import com.admin_official.codeforcesstalker.databinding.FragmentUserInfoBinding
import com.admin_official.codeforcesstalker.logic.AppViewModel
import com.admin_official.codeforcesstalker.objects.Handle
import com.google.android.material.snackbar.Snackbar
import java.util.*


private const val TAG = "De_FragmentUserInfo"
class FragmentUserInfo : Fragment(), UserInfoRecyclerViewAdapter.RVListener {

    lateinit var binding: FragmentUserInfoBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val userInfoRVAdapter = UserInfoRecyclerViewAdapter(Collections.emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.handles.observe(this) {
            if (it != null) {
                val li = it.sortedWith { a: Handle, b: Handle -> -1 * a.rating.compareTo(b.rating) }
//                Log.d(TAG, "onCreate: $it")
                userInfoRVAdapter.setHandlesList(li)
            } else {
                userInfoRVAdapter.setHandlesList(Collections.emptyList())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = userInfoRVAdapter

        val swipeGesture = object: SwipeGesture(requireContext()) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {
                    ItemTouchHelper.RIGHT -> {
                        userInfoRVAdapter.delete(viewHolder.adapterPosition)
                    }
                }
            }
        }

        ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.recyclerView)
    }


    override fun onSwipeDelete(str: String) {
//        Log.d(TAG, "onSwipeDelete: del: $str")
//        showDelDiag(str)
        viewModel.delHandle(str)
        showDelsnackB(str)
    }

    private fun showDelsnackB(str: String) {
        Snackbar.make(binding.root, "Handle '$str' deleted", Snackbar.LENGTH_LONG).setAction("Undo") {
            viewModel.addHandle(str)
        }.show()
    }

    override fun onItemClicked(handle: Handle) {
        viewModel.loadStatus(handle.username)
        findNavController().navigate(R.id.action_fragmentActivityMain3_to_fragmentUserDetail2, Bundle().apply { putParcelable(
            HANDLE_CLICKED, handle) })
    }

    fun showDelDiag(str: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Do you really want to delete handle $str")
            setPositiveButton("OK") { _, _ -> viewModel.delHandle(str)}
        }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUserInfo()
    }
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