package com.example.academy_proj2_githubapp.search.data.mappers

import android.content.Context
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.search.data.models.UserFromSearchModel
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchErrors
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchResponseData
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject

class UsersSearchMapper @Inject constructor(private val context: Context) {
    fun map(result: Result<UsersSearchResponseData, UsersSearchErrors>): Result<List<UserFromSearchModel>, String> {
        return result.mapSuccess {
            it.items
        }.mapError {
            when (it) {
                UsersSearchErrors.USERS_NOT_LOADED ->
                    context.getString(R.string.users_not_load_error)
                UsersSearchErrors.ACCESS_DENIED ->
                    context.getString(R.string.access_denied_error)
            }
        }
    }
}