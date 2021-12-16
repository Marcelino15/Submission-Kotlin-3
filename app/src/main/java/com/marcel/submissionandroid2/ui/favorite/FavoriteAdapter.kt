package com.marcel.submissionandroid2.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcel.submissionandroid2.R
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.ui.detail.DetailActivity

class FavoriteAdapter(private val listUser: List<User>, private val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgPhoto : ImageView = itemView.findViewById(R.id.img_fav_photo)
        val tvUserName : TextView = itemView.findViewById(R.id.tv_fav_username)
        val btnBtnDetail: Button = itemView.findViewById(R.id.btn_detail_fav)
        val imgDelete : ImageView = itemView.findViewById(R.id.tv_fav_del)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_favorite_user,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvUserName.text = listUser[position].login
        Glide.with(viewHolder.itemView.context)
            .load(listUser[position].avatar_url)
            .circleCrop()
            .into(viewHolder.imgPhoto)
        viewHolder.btnBtnDetail.setOnClickListener {
            val objectData = Intent(context, DetailActivity::class.java)
            objectData.putExtra("data",listUser[position].login)
            context.startActivity(objectData)
        }
        viewHolder.imgDelete.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])
        }

    }

    override fun getItemCount() = listUser.size
}