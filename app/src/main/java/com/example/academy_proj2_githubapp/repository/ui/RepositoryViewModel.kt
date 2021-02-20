package com.example.academy_proj2_githubapp.repository.ui

import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryModel
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryReadmeModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject


sealed class RepoState {
    object RepoLoading : RepoState()
    data class RepoError(val error: String) : RepoState()
    data class RepoLoaded(val data: RepositoryModel) : RepoState()
}

class RepositoryViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
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

    private fun showResult(info: Result<RepositoryModel, String>) {
        if (info.isError) {
            repoState.postValue(RepoState.RepoError(info.errorResult))
        } else {
            val operation = multithreading.async<Result<RepositoryReadmeModel, String>> {
                val result =
                    repositoryService.getRepoReadme(
                        info.successResult.owner.login,
                        info.successResult.name
                    ).execute().body()
                        ?: return@async Result.error("Readme unincluded")

                return@async Result.success(result)
            }

            operation.postOnMainThread {
                if (it.isError) {
                    info.successResult.readme = it.errorResult
                    repoState.postValue(RepoState.RepoLoaded(info.successResult))
                } else {
                    info.successResult.readme = decode(it.successResult.content)
                    repoState.postValue(RepoState.RepoLoaded(info.successResult))
                }
            }
        }
    }

    private fun decode(string: String): String {
        val result = string.replace("\\n", "", false)
        return Base64.decode(result, 0).decodeToString()
    }
}