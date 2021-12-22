package com.admin_official.codeforcesstalker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.FragmentUserInfoBinding
import java.util.*

private const val TAG = "De_FragmentUserInfo"
class FragmentUserInfo : Fragment() {

    lateinit var binding: FragmentUserInfoBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val userInfoRVAdapter = UserInfoRecyclerViewAdapter(Collections.emptyList())
    private lateinit var userInfoReView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*viewModel.usernames.observe(this, {
            Log.d(TAG, "onCreate: $it")
            viewModel.loadHandles(it)
        })*/

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
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUserInfo()
    }
}