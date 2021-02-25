package com.example.academy_proj2_githubapp.repository.data.models

import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    @SerializedName("id")
    val id: Int,

    var readme: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: UserInfoModel,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String,

    @SerializedName("stargazers_url")
    val stargazersUrl: String,

    @SerializedName("contributors_url")
    val contributorsUrl: String,

    @SerializedName("issue_comment_url")
    val issueCommentUrl: String,

    @SerializedName("issues_url")
    val issuesUrl: String,

    @SerializedName("stargazers_count")
    val stargazersCount: Int,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("forks")
    val forks: Int,

    @SerializedName("open_issues")
    val openIssues: Int,

    @SerializedName("watchers")
    val watchers: Int,

    @SerializedName("subscribers_count")
    val subscribersCount: Int,
)