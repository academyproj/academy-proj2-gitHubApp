package com.example.academy_proj2_githubapp.user_profile.data.api

import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UsersService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{user}")
    fun getUser(@Path("user") user: String): Call<UserInfoModel>
}