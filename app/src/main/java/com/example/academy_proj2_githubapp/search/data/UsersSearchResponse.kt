package com.example.academy_proj2_githubapp.search.data

import com.google.gson.annotations.SerializedName

data class UsersSearchResponseData(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserFromSearchData>,
    @SerializedName("total_count")
    val totalCount: Int
)

data class UserFromSearchData(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String
)