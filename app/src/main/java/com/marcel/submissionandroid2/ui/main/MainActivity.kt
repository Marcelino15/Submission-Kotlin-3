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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marcel.submissionandroid2.R
import com.marcel.submissionandroid2.databinding.ActivityMainBinding
import com.marcel.submissionandroid2.repository.UserRepository
import com.marcel.submissionandroid2.ui.favorite.FavoriteActivity
import androidx.datastore.preferences.core.Preferences
import com.marcel.submissionandroid2.SettingPreferences
import com.marcel.submissionandroid2.ThemeViewModel
import com.marcel.submissionandroid2.ViewModelFactory2

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var isSwitch:Boolean = false

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

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory2(pref)).get( ThemeViewModel::class.java)
        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    isSwitch = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    isSwitch = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory2(pref)).get( ThemeViewModel::class.java)
        when (item.itemId) {
            R.id.menu1 -> {

                    themeViewModel.saveThemeSetting(true)

//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }
            R.id.menu2 -> {

                    themeViewModel.saveThemeSetting(false)

//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                return true
            }
            R.id.menu3 -> {
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

