package com.marcel.submissionandroid2.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.marcel.submissionandroid2.R
import com.marcel.submissionandroid2.SectionsPagerAdapter
import com.marcel.submissionandroid2.databinding.ActivityDetailBinding
import com.marcel.submissionandroid2.ui.main.MainViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        setTitle("Detail User")

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
            binding.tvDetailCompany.text = list.company
            binding.tvDetailLocation.text = list.location
        })

        binding?.fabAdd?.setOnClickListener {
            viewModel.detailUser.observe(this,{ list ->
                Log.d("detail_user", list.name)
            })

        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}