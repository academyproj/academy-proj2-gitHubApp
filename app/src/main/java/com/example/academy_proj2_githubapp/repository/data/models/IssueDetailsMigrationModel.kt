package com.example.academy_proj2_githubapp.repository.data.models

import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.comments.models.CommentReactions
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class IssueDetailsMigrationModel(
    @SerializedName("body")
    val body: String,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("reactions")
    val reactions: CommentReactions,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("repository_url")
    val repoUrl: String,
    @SerializedName("user")
    val user: UserInfoModel
)

fun IssueDetailsMigrationModel.toCommentModel(): CommentModel {
    return CommentModel(
        body = body,
        id = id,
        user = user,
        createdAt = createdAt,
        reactions = reactions,
        updatedAt = updatedAt
    )
}
