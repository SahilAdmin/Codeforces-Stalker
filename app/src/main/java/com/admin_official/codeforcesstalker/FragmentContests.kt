package com.admin_official.codeforcesstalker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.admin_official.codeforcesstalker.adapters.ContestInfoRecyclerViewAdapter
import com.admin_official.codeforcesstalker.data.Username
import com.admin_official.codeforcesstalker.databinding.FragmentContestsBinding
import com.admin_official.codeforcesstalker.logic.AppViewModel
import com.admin_official.codeforcesstalker.objects.Contest
import java.util.*

class FragmentContests : Fragment(), ContestInfoRecyclerViewAdapter.RVListener{

    lateinit var adapter: ContestInfoRecyclerViewAdapter
    lateinit var binding: FragmentContestsBinding
    val viewModel: AppViewModel by activityViewModels()
    lateinit var handles: List<Username>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ContestInfoRecyclerViewAdapter(Collections.emptyList(), this)
        viewModel.pUsernames.observe(this, {
            handles = it
        })

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

    override fun onItemClicked(contest: Contest) {
        if(contest.phase == "BEFORE") {
            Toast.makeText(context, "Contest not started yet", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.loadStandings(contest.id, handles)
            findNavController().navigate(R.id.action_fragmentActivityMain3_to_fragmentContestStatus,
                Bundle().apply {putParcelable(CONTEST_CLICKED, contest)})
        }
    }
}