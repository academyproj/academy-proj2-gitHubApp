package com.example.academy_proj2_githubapp.comments.models


import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class CommentModel(
    @SerializedName("body")
    val body: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reactions")
    val reactions: CommentReactions,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: UserInfoModel
)