package com.example.academy_proj2_githubapp.repository.data.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class RepositoryReadmeModel(

    @SerializedName("content")
    @Expose
    var content: String,

    @SerializedName("encoding")
    @Expose
    var encoding: String,
)