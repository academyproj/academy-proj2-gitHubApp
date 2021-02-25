package com.example.academy_proj2_githubapp.user_profile.data.mappers

import android.content.Context
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoErrors
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import javax.inject.Inject

class UserProfileMapper @Inject constructor(private val context: Context) {
    fun map(result: Result<UserInfoModel, UserInfoErrors>): Result<UserInfoModel, String> {
        return result.mapSuccess {

            val name = it.name ?: it.login
            val company = it.company ?: context.getString(R.string.no_company)
            val location = it.location ?: context.getString(R.string.no_location)
            val bio = it.bio ?: ""

            UserInfoModel(
                id = it.id,
                login = it.login,
                name = name,
                avatarUrl = it.avatarUrl,
                company = company,
                email = it.email,
                location = location,
                bio = bio,
                followers = it.followers,
                following = it.following,
                follow = context.getString(R.string.login_follow_template, it.followers, it.following)
            )
        }.mapError {
            val errorResId = when (it) {
                UserInfoErrors.USER_INFO_NOT_LOADED -> R.string.user_info_not_loaded
                else -> R.string.unknown_error
            }
            context.getString(errorResId)
        }
    }
}