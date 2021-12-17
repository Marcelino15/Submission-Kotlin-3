package com.marcel.submissionandroid2.api

import com.marcel.submissionandroid2.DetailUserResponse
import com.marcel.submissionandroid2.FollowersResponseItem
import com.marcel.submissionandroid2.FollowingResponseItem
import com.marcel.submissionandroid2.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun searchUser(
        @Query("q") username: String?,
        @Header("Authorization") token: String?
    ) : Call <GithubResponse>

    @GET("/users/{username}")
    fun detailByUsername(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ) : Call <DetailUserResponse>

    @GET("/users/{username}/followers")
    fun findUserDetailFollower(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ) : Call <List<FollowersResponseItem>>

    @GET("/users/{username}/following")
    fun findUserDetailFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ) : Call <List<FollowingResponseItem>>
}