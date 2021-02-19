package com.example.academy_proj2_githubapp.login.data

import com.example.academy_proj2_githubapp.login.data.models.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


//TODO refrac onto different features or delete
interface UserService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repositories")
    suspend fun getRepos(@Header("Authorization") auth: String): User
}