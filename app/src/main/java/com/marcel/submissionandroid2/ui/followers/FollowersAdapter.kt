package com.marcel.submissionandroid2.ui.followers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcel.submissionandroid2.FollowersResponseItem
import com.marcel.submissionandroid2.R

class FollowersAdapter(private val listFollowers: List<FollowersResponseItem>, private val context: FollowersFragment) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgPhoto : ImageView = itemView.findViewById(R.id.img_follower_photo)
        val tvUserName : TextView = itemView.findViewById(R.id.tv_follower_username)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view : View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follower,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUserName.text = listFollowers[position].login
        Glide.with(viewHolder.itemView.context)
            .load(listFollowers[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgPhoto)
    }

    override fun getItemCount() = listFollowers.size
}