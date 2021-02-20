package com.example.academy_proj2_githubapp.repository.data

import com.example.academy_proj2_githubapp.repository.data.models.RepositoryModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryReadmeModel
import retrofit2.Call
import retrofit2.http.*

interface RepositoryService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryModel>


    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}/readme")
    fun getRepoReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryReadmeModel>
}