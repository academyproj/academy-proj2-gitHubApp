package com.example.academy_proj2_githubapp.repository.ui.issues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.models.IssueModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject

class IssuesViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
) : ViewModel() {

    val viewState = MutableLiveData<IssuesViewState>()

    fun getIssues(owner: String, repo: String) {
        viewState.postValue(IssuesViewState.IssuesLoading)

        val operation = multithreading.async<Result<List<IssueModel>, String>> {
            val users = repositoryService.getRepoIssues(owner, repo).execute().body()
                ?: return@async Result.error("Issues not loaded")
            return@async Result.success(users)
        }

        operation.postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<List<IssueModel>, String>) {
        viewState.value = if (result.isError) {
            IssuesViewState.IssuesFailed(result.errorResult)
        } else {
            IssuesViewState.IssuesSuccess(result.successResult)
        }
    }
}

sealed class IssuesViewState {
    object IssuesLoading : IssuesViewState()
    data class IssuesSuccess(val data: List<IssueModel>) : IssuesViewState()
    data class IssuesFailed(val error: String) : IssuesViewState()
}