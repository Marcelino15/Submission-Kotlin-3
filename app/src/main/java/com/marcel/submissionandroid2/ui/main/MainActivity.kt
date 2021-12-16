package com.marcel.submissionandroid2.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marcel.submissionandroid2.R
import com.marcel.submissionandroid2.databinding.ActivityMainBinding
import com.marcel.submissionandroid2.repository.UserRepository
import com.marcel.submissionandroid2.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setTitle("Github User's Search")

        val mUserRepository = UserRepository(application)
        mUserRepository.getAllUsers().observe(this, Observer {
            println("Data Favorite : ${Gson().toJson(it)} ")
        })

        viewModel.isLoading.observe(this,{
            showLoading(it)
        })

        binding.btnSend.setOnClickListener { view ->
            viewModel.getUserGithub(binding.edSearch.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)

        }

        viewModel.listUser.observe(this, Observer { list ->
            val adapter = SearchUserAdapter(list,this)
            binding.rvListUsers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvListUsers.adapter = adapter
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean){
       binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

