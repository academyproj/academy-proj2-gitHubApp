package com.example.academy_proj2_githubapp.repository.ui.repository

import android.content.Context
import android.util.Base64
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryReadmeModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryUiModel
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject

class RepoToUiMapper @Inject constructor(
    private val context: Context
) {

    fun map(repoInfo: RepositoryModel): RepositoryUiModel {
        return RepositoryUiModel(
            repoName = repoInfo.name,
            repoDescription = repoInfo.description ?: "No description provided",
            ownerName = repoInfo.owner.login,
            ownerIconUrl = repoInfo.owner.avatarUrl,
            issuesCount = context.getString(
                R.string.issues_count_template,
                repoInfo.openIssuesCount
            ),
            readme = repoInfo.readme?.let { decode(it) } ?: "Readme not included",
            starsCount = if (repoInfo.stargazersCount % 10 == 1) {
                context.getString(R.string.star_count_template, repoInfo.stargazersCount)
            } else {
                context.getString(R.string.stars_count_template, repoInfo.stargazersCount)
            },
            forksCount = if (repoInfo.forksCount % 10 == 1) {
                context.getString(R.string.fork_count_template, repoInfo.forksCount)
            } else {
                context.getString(R.string.forks_count_template, repoInfo.forksCount)
            }
        )
    }

    private fun decode(string: String): String {
        val result = string.replace("\\n", "", false)
        return Base64.decode(result, 0).decodeToString()
    }

}