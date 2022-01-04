package com.admin_official.codeforcesstalker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.ContestHandleDetailBinding
import com.admin_official.codeforcesstalker.objects.ContestHandle

class ContestHandleDetailViewHolder(private val binding: ContestHandleDetailBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(handle: ContestHandle) {
        binding.contestHandleName.text = "Name: "+handle.name
        binding.contestHandleRank.text = "Rank: "+handle.rank.toString()
        binding.contestHandlePenalty.text = "Penalty: "+handle.penalty.toString()
        binding.contestHandlePS.text = "Problems Solved: "+handle.problemSolved.toString()
    }
}

class ContestStatusRecyclerViewAdapter(var handles: List<ContestHandle>): RecyclerView.Adapter<ContestHandleDetailViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContestHandleDetailViewHolder {
        val binding = ContestHandleDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContestHandleDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContestHandleDetailViewHolder, position: Int) {
        holder.bind(handles[position])
    }

    override fun getItemCount(): Int = handles.size

    fun setHandlesList(handles: List<ContestHandle>) {
        this.handles = handles
        notifyDataSetChanged()
    }
}