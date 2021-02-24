package com.example.academy_proj2_githubapp.comments.models


import com.example.academy_proj2_githubapp.reactions.models.ReactionType
import com.google.gson.annotations.SerializedName

data class CommentReactions(
    @SerializedName("confused")
    val confused: Int,
    @SerializedName("eyes")
    val eyes: Int,
    @SerializedName("heart")
    val heart: Int,
    @SerializedName("hooray")
    val hooray: Int,
    @SerializedName("laugh")
    val laugh: Int,
    @SerializedName("rocket")
    val rocket: Int,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("+1")
    val plusOne: Int,
    @SerializedName("-1")
    val minusOne: Int
)

fun CommentReactions.toMap(): HashMap<ReactionType, Int>{
    return hashMapOf(
        ReactionType.CONFUSED to confused,
        ReactionType.EYES to eyes,
        ReactionType.HEART to heart,
        ReactionType.HOORAY to hooray,
        ReactionType.LAUGH to laugh,
        ReactionType.ROCKET to rocket,
        ReactionType.PLUS_ONE to plusOne,
        ReactionType.MINUS_ONE to minusOne
    )
}