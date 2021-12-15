package com.marcel.submissionandroid2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)
    fun getAllNotes(): LiveData<List<User>> = mUserRepository.getAllUsers()
}