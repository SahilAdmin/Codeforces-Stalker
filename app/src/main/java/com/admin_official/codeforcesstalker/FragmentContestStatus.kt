package com.admin_official.codeforcesstalker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.admin_official.codeforcesstalker.adapters.ContestStatusRecyclerViewAdapter
import com.admin_official.codeforcesstalker.databinding.FragmentContestStatusBinding
import com.admin_official.codeforcesstalker.logic.AppViewModel
import com.admin_official.codeforcesstalker.objects.Contest
import com.admin_official.codeforcesstalker.objects.timeStampUtil
import java.util.*

const val CONTEST_CLICKED = "contest_clicked"

class FragmentContestStatus : Fragment() {

    lateinit var binding: FragmentContestStatusBinding
    lateinit var contest: Contest
    val viewModel: AppViewModel by activityViewModels()
    lateinit var adapter: ContestStatusRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            contest = (getParcelable(CONTEST_CLICKED)!!)
        }

        adapter = ContestStatusRecyclerViewAdapter(Collections.emptyList())

        viewModel.standings.observe(this, {
            if(it != null) {
                adapter.setHandlesList(it)
                viewModel.nullContestStandings()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContestStatusBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.contestStatusName.text = contest.name
        binding.contestStatusDuration2.text = contest.duration.toString()
        binding.contestsStatusTime.text = timeStampUtil(contest.timeStamp*1000L, "dd/MM/yyyy, HH:mm")

        binding.contestStatusRV.layoutManager = LinearLayoutManager(context)
        binding.contestStatusRV.adapter = adapter
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = FragmentContestStatus()
    }
}