package com.example.academy_proj2_githubapp.user_profile.data.api

import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserInfoService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Call<UserInfoModel>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{user}/repos")
    fun getUserRepos(@Path("user") user: String): Call<List<UserRepoModel>>
}