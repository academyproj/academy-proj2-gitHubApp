package com.example.academy_proj2_githubapp.repository.data.models

import com.google.gson.annotations.SerializedName


// TODO clear useless fields

data class RepositoryModel(
    @SerializedName("id")
    val id: Int,

    var readme: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: UserModel,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String,

    @SerializedName("forks_url")
    val forksUrl: String,

    @SerializedName("keys_url")
    val keysUrl: String,

    @SerializedName("collaborators_url")
    val collaboratorsUrl: String,

    @SerializedName("teams_url")
    val teamsUrl: String,

    @SerializedName("hooks_url")
    val hooksUrl: String,

    @SerializedName("issue_events_url")
    val issueEventsUrl: String,

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("assignees_url")
    val assigneesUrl: String,

    @SerializedName("branches_url")
    val branchesUrl: String,

    @SerializedName("tags_url")
    val tagsUrl: String,

    @SerializedName("blobs_url")
    val blobsUrl: String,

    @SerializedName("git_tags_url")
    val gitTagsUrl: String,

    @SerializedName("git_refs_url")
    val gitRefsUrl: String,

    @SerializedName("trees_url")
    val treesUrl: String,

    @SerializedName("statuses_url")
    val statusesUrl: String,

    @SerializedName("languages_url")
    val languagesUrl: String,

    @SerializedName("stargazers_url")
    val stargazersUrl: String,

    @SerializedName("contributors_url")
    val contributorsUrl: String,

    @SerializedName("comments_url")
    val commentsUrl: String,

    @SerializedName("issue_comment_url")
    val issueCommentUrl: String,

    @SerializedName("issues_url")
    val issuesUrl: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("pushed_at")
    val pushedAt: String,

    @SerializedName("stargazers_count")
    val stargazersCount: Int,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    @SerializedName("has_issues")
    val hasIssues: Boolean,

    @SerializedName("has_projects")
    val hasProjects: Boolean,

    @SerializedName("has_pages")
    val hasPages: Boolean,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("disabled")
    val disabled: Boolean,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("forks")
    val forks: Int,

    @SerializedName("open_issues")
    val openIssues: Int,

    @SerializedName("watchers")
    val watchers: Int,

    @SerializedName("default_branch")
    val defaultBranch: String,

    @SerializedName("subscribers_count")
    val subscribersCount: Int,
)