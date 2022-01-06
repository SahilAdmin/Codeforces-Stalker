package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.*
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

        viewModel.handles.observe(this, {
            if(it != null) {
                val li = it.sortedWith { a: Handle, b: Handle -> -1*a.rating.compareTo(b.rating) }
//                Log.d(TAG, "onCreate: $it")
                userInfoRVAdapter.setHandlesList(li)
            } else {
                userInfoRVAdapter.setHandlesList(Collections.emptyList())
            }
        })
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
        Snackbar.make(binding.root, "Handle '$str' deleted", Snackbar.LENGTH_INDEFINITE).setAction("Undo") {
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