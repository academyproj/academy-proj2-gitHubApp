package com.example.academy_proj2_githubapp.reactions.models

import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.google.gson.annotations.SerializedName

data class ReactionModel(
    val id: Int,
    val createdAt: String,
    val user: UserInfoModel,
    val type: ReactionType
)
