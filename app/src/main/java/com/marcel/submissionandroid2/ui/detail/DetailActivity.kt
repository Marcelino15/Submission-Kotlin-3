package com.marcel.submissionandroid2.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.marcel.submissionandroid2.R
import com.marcel.submissionandroid2.SectionsPagerAdapter
import com.marcel.submissionandroid2.ViewModelFactory
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.databinding.ActivityDetailBinding
import com.marcel.submissionandroid2.helper.DateHelper
import com.marcel.submissionandroid2.ui.insert.UserAddViewModel
import com.marcel.submissionandroid2.ui.main.MainViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userAddViewModel: UserAddViewModel
    private var user = User()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("Detail User")
        supportActionBar?.hide()

        val login = intent.getStringExtra("data");

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.isLoading.observe(this,{
            showLoading(it)
        })

        viewModel.detailUser(login.toString())

        viewModel.detailUser.observe(this,{ list ->
            Glide.with(binding.imgDetailPhoto.context)
                .load(list.avatarUrl)
                .circleCrop()
                .into(binding.imgDetailPhoto)
            binding.tvDetailName.text = list.name
            binding.tvDetailUserName.text = list.login
            binding.tvDetailCompany.text = list.company
            binding.tvDetailLocation.text = list.location

            val sectionsPagerAdapter = SectionsPagerAdapter(this)

            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter

            val tabs: TabLayout = binding.tabs

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        })

        userAddViewModel = obtainViewModel(this@DetailActivity)

        binding?.fabAdd?.setOnClickListener {
            viewModel.detailUser.observe(this,{ list ->
                println("Detail user : ${Gson().toJson(list)}")
                //Log.d("detail_user", list.name)
                user.id = list.id
                user.login = list.login
                user.avatar_url = list.avatarUrl
                user.date = DateHelper.getCurrentDate()
                userAddViewModel.insert(user as User)
                showToast(getString(R.string.added))
                finish()
            })

        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserAddViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserAddViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}