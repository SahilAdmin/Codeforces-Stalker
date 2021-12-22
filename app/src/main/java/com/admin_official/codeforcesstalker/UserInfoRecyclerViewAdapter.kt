package com.admin_official.codeforcesstalker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.UserInfoBinding
import com.bumptech.glide.Glide

class UserInfoViewHolder(val binding: UserInfoBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(handle: Handle) {
        binding.userInfoName.text = handle.username
        binding.userInfoRank.text = handle.rank
        binding.userInfoRating.text = handle.rating.toString()
        binding.userInfoMaxRank.text = binding.root.context.getString(R.string.userInfo_maxRank, handle.maxRank)
        binding.userInfoMaxRating.text = binding.root.context.getString(R.string.userInfo_maxRating, handle.maxRating)
        binding.userInfoSubmissions.text = binding.root.context.getString(R.string.userInfo_submissions, handle.submissionsToday)
        binding.userInfoAccepted.text = binding.root.context.getString(R.string.userInfo_accepted, handle.acceptedToday)

        Glide.with(binding.root.context)
            .load(handle.dp)
            .into(binding.userInfoProfile)
    }
}

private const val TAG = "UserInfo_RVAdapter"
class UserInfoRecyclerViewAdapter(var handles: List<Handle>): RecyclerView.Adapter<UserInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val binding = UserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        holder.bind(handles[position])
    }

    override fun getItemCount(): Int = handles.size

    fun setHandlesList(list: List<Handle>) {
        this.handles = list
        notifyDataSetChanged()
    }
}