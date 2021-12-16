package com.marcel.submissionandroid2.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcel.submissionandroid2.ViewModelFactory
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.databinding.ActivityFavoriteBinding
import com.marcel.submissionandroid2.ui.insert.UserAddViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAddViewModel: UserAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Favorite User")
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAddViewModel = obtainViewModel(this@FavoriteActivity)
        userAddViewModel.getAllUsers().observe(this, Observer { list ->
            val adapter = FavoriteAdapter(list,this)
            binding.rvListUserFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvListUserFavorite.adapter = adapter
            val listUserAdapter = FavoriteAdapter(list,this)
            binding.rvListUserFavorite.adapter = listUserAdapter
            listUserAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    userAddViewModel.delete(data)
                }
            })
        })
    }
    private fun obtainViewModel(activity: AppCompatActivity): UserAddViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserAddViewModel::class.java)
    }

}