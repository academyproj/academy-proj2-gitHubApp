package com.example.academy_proj2_githubapp.user_profile.data.models

import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class UserRepoModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: UserInfoModel,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("watchers_count")
    val watchersCount: Int
)