package com.example.academy_proj2_githubapp.repository.ui.contributors

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import javax.inject.Inject

class ContributorsViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
) : ViewModel() {

    val viewState = MutableLiveData<ContributorsViewState>()

    fun getContributors(owner: String, repo: String) {
        viewState.postValue(ContributorsViewState.ContributorsLoading)

        val operation = multithreading.async<Result<List<UserInfoModel>, String>> {
            val users = repositoryService.getRepoContributors(owner, repo).execute().body()
                ?: return@async Result.error("Contributors not loaded")
            return@async Result.success(users)
        }

        operation.postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<List<UserInfoModel>, String>) {
        viewState.value = if (result.isError) {
            ContributorsViewState.ContributorsFailed(result.errorResult)
        } else {
            ContributorsViewState.ContributorsSuccess(result.successResult)
        }
    }

}

sealed class ContributorsViewState {
    object ContributorsLoading : ContributorsViewState()
    data class ContributorsSuccess(val data: List<UserInfoModel>) : ContributorsViewState()
    data class ContributorsFailed(val error: String) : ContributorsViewState()
}