package com.example.academy_proj2_githubapp.repository.data.models

import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.comments.models.CommentReactions
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class IssueDetailsMigrationModel(
    @SerializedName("body")
    val body: String,
    @SerializedName("closed_at")
    val closedAt: Any?,
    @SerializedName("closed_by")
    val closedBy: Any?,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("labels")
    val labels: List<Any>,
    @SerializedName("locked")
    val locked: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("reactions")
    val reactions: CommentReactions,
    @SerializedName("state")
    val state: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
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
