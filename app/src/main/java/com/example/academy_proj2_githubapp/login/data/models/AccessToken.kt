package com.example.academy_proj2_githubapp.login.data.models

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("token_type") val tokenType: String,
)