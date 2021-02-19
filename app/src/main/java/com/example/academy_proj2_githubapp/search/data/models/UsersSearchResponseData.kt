package com.example.academy_proj2_githubapp.search.data.models

import com.google.gson.annotations.SerializedName

data class UsersSearchResponseData(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserFromSearchModel>,
    @SerializedName("total_count")
    val totalCount: Int
)

data class UserFromSearchModel(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val login: String,
)