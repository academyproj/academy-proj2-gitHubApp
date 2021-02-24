package com.example.academy_proj2_githubapp.repository.data

import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.repository.data.models.*
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RepositoryService {

    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryModel>


    @GET("repos/{owner}/{repo}/readme")
    fun getRepoReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryReadmeModel>

    @GET("repos/{owner}/{repo}/issues")
    fun getRepoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<List<IssueModel>>


    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    fun getRepoIssueComments(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("number") issueNumber: Int,
    ): Call<List<CommentModel>>

    @GET("repos/{owner}/{repo}/issues/{number}")
    fun getIssueDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("number") issueNumber: Int,
    ): Call<IssueDetailsMigrationModel>

    @POST("repos/{owner}/{repo}/issues/comments/{id}/reactions")
    fun createIssueCommentReaction(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("id") id: Int,
        @Body content: ReactionContent
    ) : Call<ReactionData>
}

data class ReactionContent(
    @SerializedName("content")
    val content: String
)