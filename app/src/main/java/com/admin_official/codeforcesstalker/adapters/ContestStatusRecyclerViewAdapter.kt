package com.admin_official.codeforcesstalker.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.R
import com.admin_official.codeforcesstalker.databinding.ContestHandleDetailBinding
import com.admin_official.codeforcesstalker.objects.ContestHandle

class ContestHandleDetailViewHolder(private val binding: ContestHandleDetailBinding): RecyclerView.ViewHolder(binding.root) {

    val context = binding.root.context
    fun bind(handle: ContestHandle) {
        val str = context.getString(R.string.contestStatusProblems)
        binding.contestHandleName.text =
            Html.fromHtml(context.getString(R.string.contestStatusName, handle.name))
        binding.contestHandleRank.text =
            Html.fromHtml(context.getString(R.string.contestStatusRank, handle.rank.toString()))
        binding.contestHandlePenalty.text =
            Html.fromHtml(context.getString(R.string.contestStatusPenalty, handle.penalty.toString()))
        binding.contestHandlePS.text =
            Html.fromHtml(context.getString(R.string.contestStatusProblems, handle.problemSolved.toString()))
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