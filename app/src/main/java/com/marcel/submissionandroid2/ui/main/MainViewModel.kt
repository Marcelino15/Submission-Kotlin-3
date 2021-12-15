package com.marcel.submissionandroid2.ui.main


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcel.submissionandroid2.*
import com.marcel.submissionandroid2.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser =  MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _listUserFollowing = MutableLiveData<List<FollowingResponseItem>>()
    val listUserFollowing: LiveData<List<FollowingResponseItem>> = _listUserFollowing

    private val _listUserFollowers = MutableLiveData<List<FollowersResponseItem>>()
    val listUserFollowers: LiveData<List<FollowersResponseItem>> = _listUserFollowers

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    companion object {
        private const val TOKEN = "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"
    }

//    private val mUserRepository: UserRepository = UserRepository(application)

//    fun getAllUsers(): LiveData<List<User>> = mUserRepository.getAllUsers()

    fun getUserGithub(username: String?){
        _isloading.value = true
        val client = ApiConfig.getApiService().searchUser(username, TOKEN)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
               _isloading.value = false
                if(response.isSuccessful){
                    _listUser.value = response.body()?.items

                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isloading.value=false
                Log.d(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun detailUser(username: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().detailByUsername(username, TOKEN)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isloading.value = false
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isloading.value=false
                Log.d(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun listUserFollowing(username: String){
        _isloading.value = true
        val client = ApiConfig.getApiService().findUserDetailFollowing(username, TOKEN)
        client.enqueue(object : Callback<List<FollowingResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isloading.value = false
                if(response.isSuccessful){
                    _listUserFollowing.value = response.body()
                    Log.d("hasil", response.body().toString())
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isloading.value = false
                Log.d(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    fun listUserFollowers(username: String){
        _isloading.value = true
        val client = ApiConfig.getApiService().findUserDetailFollower(username, TOKEN)
        client.enqueue(object  : Callback<List<FollowersResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
               if(response.isSuccessful){
                   _isloading.value = false
                   _listUserFollowers.value = response.body()
                   Log.d("hasil",response.body().toString())
               } else {
                   Log.e(TAG, "onFailure : ${response.message()}")
               }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isloading.value = false
                Log.d(TAG, "onFailure : ${t.message.toString()}")
            }

        })

    }

}


