package com.example.academy_proj2_githubapp.search.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchUsersService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/users")
    fun searchUsers(
        @Query("q") searchKeyword: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Call<UsersSearchResponseData>
}
