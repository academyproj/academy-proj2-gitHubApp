package com.example.academy_proj2_githubapp.shared.models

import com.google.gson.annotations.SerializedName


data class UserInfoModel(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("company")
    val company: String?,
    @SerializedName("email")
    val email: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String?,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,

    val follow: String,
)