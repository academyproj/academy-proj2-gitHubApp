package com.example.academy_proj2_githubapp.search.data.api

import com.example.academy_proj2_githubapp.search.data.models.UsersSearchResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUsersService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") searchKeyword: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Call<UsersSearchResponseData>
}
