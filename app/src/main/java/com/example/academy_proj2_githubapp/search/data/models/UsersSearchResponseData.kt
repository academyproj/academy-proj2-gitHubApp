package com.example.academy_proj2_githubapp.search.data.models

import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class UsersSearchResponseData(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserInfoModel>,
    @SerializedName("total_count")
    val totalCount: Int
)