package com.marcel.submissionandroid2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowingAdapter(private val listFollowing: List<FollowingResponseItem>, private val context: FollowingFragment) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPhoto : ImageView = itemView.findViewById(R.id.img_following_photo)
        val tvUserName : TextView = itemView.findViewById(R.id.tv_following_username)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_following,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       viewHolder.tvUserName.text = listFollowing[position].login
        Glide.with(viewHolder.itemView.context)
            .load(listFollowing[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgPhoto)
    }

    override fun getItemCount() = listFollowing.size
}