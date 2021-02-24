package com.example.academy_proj2_githubapp.repository.data

import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.repository.data.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ContributorsService {

    @GET("repos/{owner}/{repo}/contributors")
    fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<List<UserModel>>

    @Headers("Accept: application/vnd.github.squirrel-girl-preview+json")
    @GET("repos/{owner}/{repo}/issues/comments/{id}/reactions")
    fun getCommentReactions(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("id") id: Int
    ): Call<List<ReactionData>>
}