package com.marcel.submissionandroid2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.databinding.ActivityFavoriteBinding
import com.marcel.submissionandroid2.ui.insert.UserAddViewModel

class UserFavAddActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var user: User? = null

    private lateinit var userAddViewModel: UserAddViewModel
    private var _activityUserAddBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityUserAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityUserAddBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        userAddViewModel = obtainViewModel(this@UserFavAddActivity)


    }

    override fun onDestroy() {
        super.onDestroy()
        _activityUserAddBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserAddViewModel::class.java)
    }
}