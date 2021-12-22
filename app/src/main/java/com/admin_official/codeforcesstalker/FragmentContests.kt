package com.admin_official.codeforcesstalker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.admin_official.codeforcesstalker.databinding.FragmentContestsBinding
import java.util.*

class FragmentContests : Fragment() {

    lateinit var binding: FragmentContestsBinding
    val adapter = ContestInfoRecyclerViewAdapter(Collections.emptyList())
    val viewModel: AppViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.contests.observe(this, {
            if(it != null) {
                adapter.setContestsList(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContestsBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contestRV.layoutManager = LinearLayoutManager(activity)
        binding.contestRV.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentContests()
    }
}