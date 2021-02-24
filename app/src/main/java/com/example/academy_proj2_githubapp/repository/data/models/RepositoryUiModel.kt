package com.example.academy_proj2_githubapp.repository.data.models

data class RepositoryUiModel(
    val repoName: String,
    val repoDescription: String?,
    val ownerName: String,
    val ownerIconUrl: String,
    val issuesCount: String,
    val readme: String,
    val starsCount: String,
    val forksCount: String,
)