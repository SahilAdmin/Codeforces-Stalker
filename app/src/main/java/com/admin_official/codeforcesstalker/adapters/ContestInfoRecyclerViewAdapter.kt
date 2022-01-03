package com.admin_official.codeforcesstalker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.ContestInfoBinding
import com.admin_official.codeforcesstalker.objects.Contest
import com.admin_official.codeforcesstalker.objects.timeStampUtil

class ContestInfoViewHolder(private val binding: ContestInfoBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind (contest: Contest) {
        binding.contestInfoName.text = contest.name
        binding.contestInfoTime.text = timeStampUtil(contest.timeStamp*1000L, "dd/MM/yyyy, HH:mm")
        binding.contestInfoDuration.text = contest.duration.toString()
        binding.contestInfoPhase.text = contest.phase
    }
}

class ContestInfoRecyclerViewAdapter(var contests: List<Contest>): RecyclerView.Adapter<ContestInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestInfoViewHolder {
        val binding = ContestInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContestInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContestInfoViewHolder, position: Int) {
        holder.bind(contests[position])
    }

    override fun getItemCount(): Int = contests.size

    fun setContestsList(contests: List<Contest>) {
        this.contests = contests
        notifyDataSetChanged()
    }
}