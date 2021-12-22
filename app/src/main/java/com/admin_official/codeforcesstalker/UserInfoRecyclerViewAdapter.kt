package com.admin_official.codeforcesstalker

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.admin_official.codeforcesstalker.databinding.UserInfoBinding
import com.bumptech.glide.Glide
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

abstract class SwipeGesture(val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.deleteColor))
            .addSwipeRightActionIcon(R.drawable.ic_round_delete_sweep_24)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

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
class UserInfoRecyclerViewAdapter(var handles: List<Handle>, val listener: RV_listener): RecyclerView.Adapter<UserInfoViewHolder>() {

    interface RV_listener {
        fun onSwipeDelete(str: String)
    }

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

    fun delete(pos: Int) {
        listener.onSwipeDelete(handles[pos].username)
    }
}