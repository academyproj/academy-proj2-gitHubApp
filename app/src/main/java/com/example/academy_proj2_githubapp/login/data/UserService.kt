package com.example.academy_proj2_githubapp.login.data

import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import retrofit2.http.GET

interface UserService {

    @GET("/user")
    suspend fun getUser(): UserInfoModel
}