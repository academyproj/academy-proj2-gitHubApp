package com.example.academy_proj2_githubapp.repository.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryReadmeModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryUiModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject


sealed class RepoState {
    object RepoLoading : RepoState()
    data class RepoError(val error: String) : RepoState()
    data class RepoLoaded(val data: RepositoryUiModel) : RepoState()
}

class RepositoryViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
    private val repoToUiMapper: RepoToUiMapper,
) : ViewModel() {

    val repoState = MutableLiveData<RepoState>()

    fun loadRepo(owner: String, repo: String) {
        repoState.value = RepoState.RepoLoading

        val operation =
            multithreading.async<Result<RepositoryModel, String>> {
                val result = repositoryService.getRepo(owner, repo).execute().body()
                    ?: return@async Result.error("Repo load Error")

                return@async Result.success(result)
            }

        operation.postOnMainThread(::showResult)
    }

    private fun showResult(repoModel: Result<RepositoryModel, String>) {
        if (repoModel.isError) {
            repoState.postValue(RepoState.RepoError(repoModel.errorResult))
        } else {
            val operation = multithreading.async<Result<RepositoryReadmeModel, String>> {
                val result =
                    repositoryService.getRepoReadme(
                        owner = repoModel.successResult.owner.login,
                        repo = repoModel.successResult.name
                    ).execute().body()
                        ?: return@async Result.error("Readme not included")

                return@async Result.success(result)
            }

            operation.postOnMainThread { readmeResult ->
                if (readmeResult.isError) {
                    repoState.postValue(RepoState.RepoLoaded(repoModel.mapSuccess(repoToUiMapper::map).successResult))
                } else {
                    repoModel.successResult.readme = readmeResult.successResult.content
                    repoState.postValue(RepoState.RepoLoaded(repoModel.mapSuccess(repoToUiMapper::map).successResult))
                }
            }
        }
    }
}