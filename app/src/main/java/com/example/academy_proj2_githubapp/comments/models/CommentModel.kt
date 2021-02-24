package com.example.academy_proj2_githubapp.comments.models


import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class CommentModel(
    @SerializedName("author_association")
    val authorAssociation: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("issue_url")
    val issueUrl: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("reactions")
    val reactions: CommentReactions,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: UserInfoModel
)