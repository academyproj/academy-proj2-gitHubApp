package com.example.academy_proj2_githubapp.repository.data.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class OwnerModel(
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("node_id")
    @Expose
    val nodeId: String,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("html_url")
    @Expose
    val htmlUrl: String,

    @SerializedName("followers_url")
    @Expose
    val followersUrl: String,

    @SerializedName("following_url")
    @Expose
    val followingUrl: String,

    @SerializedName("subscriptions_url")
    @Expose
    val subscriptionsUrl: String,

    @SerializedName("repos_url")
    @Expose
    val reposUrl: String,
)