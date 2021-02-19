package com.example.academy_proj2_githubapp.repository.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.models.RepositoryModel
import com.example.academy_proj2_githubapp.search.data.api.SearchUsersService
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchErrors
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchResponseData
import com.example.academy_proj2_githubapp.search.ui.SearchViewModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoErrors
import com.example.academy_proj2_githubapp.user_profile.data.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


sealed class RepoState {
    object RepoLoading : RepoState()
    class RepoError(error: String) : RepoState()
    class RepoLoaded(repoModel: RepositoryModel) : RepoState()
}

enum class Test {
    NOT_LOADED
}


class RepositoryViewModel @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
) : ViewModel() {

    val repoState = MutableLiveData<RepoState>()

    fun loadRepo(owner: String, repo: String) {
        repoState.value = RepoState.RepoLoading

        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryService.getRepo(owner, repo).execute()
            withContext(Dispatchers.Main) {
                repoState.postValue(RepoState.RepoError("test"))
            }

            Log.d("TAG", "executed ${result.body()}")
        }


        /*
        val operation =
            multithreading.async<Result<RepositoryModel, Test>> {
                val result = repositoryService.getRepo(owner, repo).execute().body()
                    ?: return@async Result.error(Test.NOT_LOADED)


                return@async Result.success(result)
            }

         */




        /*
        val operation =
            multithreading.async<Result<UsersSearchResponseData, UsersSearchErrors>> {
                val users = searchService.searchUsers(keyword,
                    SearchViewModel.USERS_PER_PAGE,
                    SearchViewModel.SEARCH_PAGES
                ).execute().body()
                    ?: return@async Result.error(UsersSearchErrors.USERS_NOT_LOADED)
                return@async Result.success(users)
            }
        operation.postOnMainThread(::showResult)

         */

        Log.d("TAG", "postonMainthred")
        //operation.postOnMainThread { Log.d("TAG", "callback") }

    }

    private fun showResult(info: Result<RepositoryModel, String>) {
        Log.d("TAG", "showres ${info.errorResult}")
        if (info.isError) {
            repoState.postValue(RepoState.RepoError(info.errorResult))
        } else {
            repoState.postValue(RepoState.RepoLoaded(info.successResult))
        }
    }

}