package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.FragmentUserInfoBinding
import java.util.*

private const val TAG = "De_FragmentUserInfo"
class FragmentUserInfo : Fragment(), UserInfoRecyclerViewAdapter.RV_listener {

    lateinit var binding: FragmentUserInfoBinding
//    lateinit var navigation: NavController
    private val viewModel: AppViewModel by activityViewModels()
    private val userInfoRVAdapter = UserInfoRecyclerViewAdapter(Collections.emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.handles.observe(this, {
//            Log.d(TAG, "onCreate: $it")
            if(it != null) {
                userInfoRVAdapter.setHandlesList(it)
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

        val swipeGesture = object: SwipeGesture(activity as AppCompatActivity) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction) {
                    ItemTouchHelper.RIGHT -> {
                        userInfoRVAdapter.delete(viewHolder.adapterPosition)
                    }
                }
            }
        }

//        navigation = Navigation.findNavController(view)

        ItemTouchHelper(swipeGesture).attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.userinfo_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add -> {
//                navigation.navigate(R.id.action_fragmentUserInfo_to_fragmentAddUser)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSwipeDelete(str: String) {
        Log.d(TAG, "onSwipeDelete: del: $str")
        viewModel.delHandle("$str")
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUserInfo()
    }
}