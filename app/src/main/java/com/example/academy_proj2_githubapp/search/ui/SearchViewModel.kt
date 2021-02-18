package com.example.academy_proj2_githubapp.search.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.search.data.SearchUsersService
import com.example.academy_proj2_githubapp.search.data.UserFromSearchData
import com.example.academy_proj2_githubapp.search.data.UsersSearchErrors
import com.example.academy_proj2_githubapp.search.data.UsersSearchResponseData
import com.example.academy_proj2_githubapp.search.data.mappers.UsersSearchMapper
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchService: SearchUsersService,
    private val multithreading: Multithreading,
    private val usersSearchMapper: UsersSearchMapper
) : ViewModel() {

    companion object {
        private const val SEARCH_PAGES = 1
        private const val USERS_PER_PAGE = 70
    }

    val viewState = MutableLiveData<SearchViewState>()

    fun searchUsers(keyword: String) {
        viewState.value = SearchViewState.SearchLoading

        val asyncOperation =
            multithreading.async<Result<UsersSearchResponseData, UsersSearchErrors>> {
                val users = searchService.searchUsers(keyword, USERS_PER_PAGE, SEARCH_PAGES)
                    .execute().body()
                    ?: return@async Result.error(UsersSearchErrors.USERS_NOT_LOADED)
                return@async Result.success(users)
            }
        asyncOperation
            .map(usersSearchMapper::map)
            .postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<List<UserFromSearchData>, String>) {
        viewState.value = if (result.isError) {
            SearchViewState.SearchFailed(result.errorResult)
        } else {
            SearchViewState.SearchSuccess(result.successResult)
        }
    }

}

sealed class SearchViewState {
    object SearchLoading : SearchViewState()
    data class SearchSuccess(val data: List<UserFromSearchData>) : SearchViewState()
    data class SearchFailed(val error: String) : SearchViewState()
}