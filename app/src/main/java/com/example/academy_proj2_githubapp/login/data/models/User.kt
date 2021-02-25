package com.example.academy_proj2_githubapp.login.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//TODO clear useless fields
data class User(
    @SerializedName("login")
    @Expose
    val login: String,

    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("name")
    @Expose
    val name: Any? = null,

    @SerializedName("location")
    @Expose
    val location: Any? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("followers")
    @Expose
    val followers: Int,

    @SerializedName("following")
    @Expose
    val following: Int,
)