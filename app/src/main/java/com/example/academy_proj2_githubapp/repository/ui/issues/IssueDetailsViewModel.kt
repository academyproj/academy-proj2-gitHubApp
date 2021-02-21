package com.example.academy_proj2_githubapp.repository.ui.issues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.models.IssueModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject

class IssueDetailsViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
) : ViewModel() {

    val viewState = MutableLiveData<IssueDetailsViewState>()

    fun getIssues(owner: String, repo: String, issue: Int) {
        viewState.postValue(IssueDetailsViewState.IssueDetailsLoading)

        val operation = multithreading.async<Result<IssueModel, String>> {
            val users = repositoryService.getRepoIssueDetails(owner, repo, issue).execute().body()
                ?: return@async Result.error("IssueDetails not loaded")
            return@async Result.success(users)
        }

        operation.postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<IssueModel, String>) {
        viewState.value = if (result.isError) {
            IssueDetailsViewState.IssueDetailsFailed(result.errorResult)
        } else {
            IssueDetailsViewState.IssueDetailsSuccess(result.successResult)
        }
    }
}

sealed class IssueDetailsViewState {
    object IssueDetailsLoading : IssueDetailsViewState()
    data class IssueDetailsSuccess(val data: IssueModel) : IssueDetailsViewState()
    data class IssueDetailsFailed(val error: String) : IssueDetailsViewState()
}