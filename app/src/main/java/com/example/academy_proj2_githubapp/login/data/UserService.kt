package com.example.academy_proj2_githubapp.login.data

import com.example.academy_proj2_githubapp.login.data.models.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface UserService {

    @GET("/user")
    suspend fun getUser(): User
}