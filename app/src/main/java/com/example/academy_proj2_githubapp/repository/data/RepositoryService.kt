package com.example.academy_proj2_githubapp.repository.data

import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.repository.data.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {
    //@Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryModel>


    //@Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}/readme")
    fun getRepoReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<RepositoryReadmeModel>

    //@Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}/contributors")
    fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<List<UserModel>>

    // @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}/issues")
    fun getRepoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Call<List<IssueModel>>

//    @GET("repos/{owner}/{repo}/issues/{number}")
//    fun getRepoIssueDetails(
//        @Path("owner") owner: String,
//        @Path("repo") repo: String,
//        @Path("number") issueNumber: Int,
//    ): Call<IssueModel>

    @GET("repos/{owner}/{repo}/issues/{number}")
    fun getRepoIssueMigrationDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("number") issueNumber: Int,
    ): Call<IssueDetailsMigrationModel>

    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    fun getIssueComments(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("number") issueNumber: Int,
    ): Call<List<CommentModel>>

    @GET("repos/{owner}/{repo}/issues/comments/{id}/reactions")
    fun getCommentReactions(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("id") id: Int
    ): Call<List<ReactionData>>
}