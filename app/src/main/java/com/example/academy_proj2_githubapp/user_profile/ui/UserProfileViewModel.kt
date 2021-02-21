package com.example.academy_proj2_githubapp.user_profile.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.user_profile.data.api.UserInfoService
import com.example.academy_proj2_githubapp.user_profile.data.mappers.UserProfileMapper
import com.example.academy_proj2_githubapp.user_profile.data.mappers.UserReposMapper
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoErrors
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val userProfileMapper: UserProfileMapper,
    private val userReposMapper: UserReposMapper,
    private val userInfoService: UserInfoService,
    private val multithreading: Multithreading
) : ViewModel() {

    val viewState = MutableLiveData<UserInfoViewState>()

    fun loadUserInfo(username: String) {
        viewState.value = UserInfoViewState.Loading

        val asyncOperation =
            multithreading.async<Result<UserInfoModel, UserInfoErrors>> {
                val user = userInfoService.getUser(username).execute().body()
                    ?: return@async Result.error(UserInfoErrors.USER_INFO_NOT_LOADED)
                return@async Result.success(user)
            }

        asyncOperation
            .map(userProfileMapper::map)
            .postOnMainThread(::loadReposList)
    }

    private fun loadReposList(userInfo: Result<UserInfoModel, String>) {
        if (userInfo.isError) {
            viewState.value = UserInfoViewState.Error(userInfo.errorResult)
        } else {
            viewState.value = UserInfoViewState.UserInfoLoaded(userInfo.successResult)

            val asyncOperation =
                multithreading.async<Result<List<UserRepoModel>, UserInfoErrors>> {
                    val repos =
                        userInfoService.getUserRepos(userInfo.successResult.login).execute().body()
                            ?: return@async Result.error(UserInfoErrors.REPOS_NOT_LOADED)
                    return@async Result.success(repos)
                }

            asyncOperation
                .map(userReposMapper::map)
                .postOnMainThread { result ->
                    if (result.isError) {
                        viewState.value = UserInfoViewState.Error(result.errorResult)
                    } else {
                        viewState.value = UserInfoViewState.ReposInfoLoaded(result.successResult)
                    }
                }

        }
    }


}

sealed class UserInfoViewState {
    object Loading : UserInfoViewState()
    data class UserInfoLoaded(val data: UserInfoModel) : UserInfoViewState()
    data class ReposInfoLoaded(val data: List<UserRepoModel>) : UserInfoViewState()
    data class Error(val error: String) : UserInfoViewState()
}