package com.example.academy_proj2_githubapp.login.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
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

    @SerializedName("starred_url")
    @Expose
    val starredUrl: String,

    @SerializedName("subscriptions_url")
    @Expose
    val subscriptionsUrl: String,

    @SerializedName("repos_url")
    @Expose
    val reposUrl: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("name")
    @Expose
    val name: Any? = null,

    @SerializedName("blog")
    @Expose
    val blog: String? = null,

    @SerializedName("location")
    @Expose
    val location: Any? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("public_repos")
    @Expose
    val publicRepos: Int,

    @SerializedName("public_gists")
    @Expose
    val publicGists: Int,

    @SerializedName("followers")
    @Expose
    val followers: Int,

    @SerializedName("following")
    @Expose
    val following: Int,

    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String? = null,
)