package com.marcel.submissionandroid2

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
import com.marcel.submissionandroid2.ui.detail.DetailActivity

class SearchUserAdapter(private val listUserSearch: List<ItemsItem>, private val context: Context) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgPhoto : ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvUserName : TextView = itemView.findViewById(R.id.tv_item_username)
        val btnBtnDetail: Button = itemView.findViewById(R.id.btn_detail)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUserName.text = listUserSearch[position].login
        Glide.with(viewHolder.itemView.context)
            .load(listUserSearch[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imgPhoto)
        viewHolder.btnBtnDetail.setOnClickListener {
            val objectData = Intent(context, DetailActivity::class.java)
            objectData.putExtra("data",listUserSearch[position].login)
            context.startActivity(objectData)
        }
    }

    override fun getItemCount() = listUserSearch.size

}