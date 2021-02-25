package com.example.academy_proj2_githubapp.search.data.mappers

import android.content.Context
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchErrors
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchResponseData
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import javax.inject.Inject

class UsersSearchMapper @Inject constructor(private val context: Context) {
    fun map(result: Result<UsersSearchResponseData, UsersSearchErrors>): Result<List<UserInfoModel>, String> {
        return result.mapSuccess {
            it.items
        }.mapError {
            val errorStringId = when (it) {
                UsersSearchErrors.USERS_NOT_LOADED -> R.string.users_not_load_error
            }
            context.getString(errorStringId)
        }
    }
}