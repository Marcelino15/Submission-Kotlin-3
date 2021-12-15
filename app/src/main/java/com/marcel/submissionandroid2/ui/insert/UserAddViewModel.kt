package com.marcel.submissionandroid2.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.marcel.submissionandroid2.database.User
import com.marcel.submissionandroid2.repository.UserRepository

class UserAddViewModel(application: Application): ViewModel() {


    private val mUserRepository: UserRepository = UserRepository(application)

    fun insert(user: User){
        mUserRepository.insert(user)
    }

    fun delete(user: User){
        mUserRepository.delete(user)
    }
}