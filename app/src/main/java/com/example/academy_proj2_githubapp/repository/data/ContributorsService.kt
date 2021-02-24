package com.example.academy_proj2_githubapp.repository.data

import com.example.academy_proj2_githubapp.repository.data.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributorsService {

    @GET("repos/{owner}/{repo}/contributors")
    fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<List<UserModel>>
}