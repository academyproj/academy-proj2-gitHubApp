package com.example.academy_proj2_githubapp.user_profile.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.user_profile.data.api.ReposListService
import com.example.academy_proj2_githubapp.user_profile.data.api.UsersService
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoErrors
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val reposListService: ReposListService,
    private val usersService: UsersService,
    private val multithreading: Multithreading
) : ViewModel() {

    val viewState = MutableLiveData<UserInfoViewState>()

    fun loadUserInfo(username: String) {
        viewState.value = UserInfoViewState.Loading

        val asyncOperation =
            multithreading.async<Result<UserInfoModel, UserInfoErrors>> {
                val user = usersService.getUser(username).execute().body()
                    ?: return@async Result.error(UserInfoErrors.USER_INFO_NOT_LOADED)
                return@async Result.success(user)
            }

        asyncOperation.postOnMainThread(::loadReposList)
    }

    private fun loadReposList(userInfo: Result<UserInfoModel, UserInfoErrors>) {
        if (userInfo.isError) {
            viewState.value = UserInfoViewState.Error("User Not Loaded")
        } else {
            viewState.value = UserInfoViewState.UserInfoLoaded(userInfo.successResult)

            val asyncOperation =
                multithreading.async<Result<List<UserRepoModel>, UserInfoErrors>> {
                    val repos =
                        reposListService.getUserRepos(userInfo.successResult.login).execute().body()
                            ?: return@async Result.error(UserInfoErrors.REPOS_NOT_LOADED)
                    return@async Result.success(repos)
                }

            asyncOperation.postOnMainThread { result ->
                if (result.isError) {
                    viewState.value = UserInfoViewState.Error("Repos Not Loaded")
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