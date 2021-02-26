package com.example.academy_proj2_githubapp.reactions.models

import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName


data class ReactionData(
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    val user: UserInfoModel
)

