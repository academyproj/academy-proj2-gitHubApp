package com.example.academy_proj2_githubapp.user_profile.data.mappers

import android.content.Context
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoErrors
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel
import javax.inject.Inject

class UserReposMapper @Inject constructor(private val context: Context) {
    fun map(result: Result<List<UserRepoModel>, UserInfoErrors>): Result<List<UserRepoModel>, String> {
        return result.mapError {
            val errorResId = when (it) {
                UserInfoErrors.REPOS_NOT_LOADED -> R.string.repos_info_not_loaded
                else -> R.string.unknown_error
            }
            context.getString(errorResId)
        }
    }
}