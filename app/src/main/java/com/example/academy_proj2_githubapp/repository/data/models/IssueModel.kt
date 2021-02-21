package com.example.academy_proj2_githubapp.repository.data.models

import com.example.academy_proj2_githubapp.login.data.models.User

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class IssueModel (
    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("html_url")
    @Expose
    val htmlUrl: String,

    @SerializedName("repository_url")
    @Expose
    val repoUrl: String,

    @SerializedName("issue_url")
    @Expose
    val issueUrl: String,

    @SerializedName("number")
    @Expose
    val number: Int,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("node_id")
    @Expose
    val nodeId: String,

    @SerializedName("user")
    @Expose
    val user: UserModel,

    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String? = null,

    @SerializedName("body")
    @Expose
    val body: String,
)