package com.example.academy_proj2_githubapp.repository.ui.issues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.reactions.models.ReactionType
import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.data.mappers.ReactionsResultMapper
import com.example.academy_proj2_githubapp.repository.data.models.IssueDetailsMigrationModel
import com.example.academy_proj2_githubapp.repository.data.models.IssueErrors
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.shared.utils.Event
import javax.inject.Inject

class IssueDetailsViewModelMigration @Inject constructor(
    private val repositoryService: RepositoryService,
    private val multithreading: Multithreading,
    private val reactionsResultMapper: ReactionsResultMapper,
) : ViewModel() {

    val viewState = MutableLiveData<IssueDetailsMigrationViewState>()
    val dialogEvent = MutableLiveData<Event<ReactionDialogViewState>>()

    fun loadIssue(owner: String, repo: String, issueId: Int) {
        viewState.value = IssueDetailsMigrationViewState.Loading

        val asyncOperation =
            multithreading.async<Result<IssueDetailsMigrationModel, IssueErrors>> {

                val issue = repositoryService.getIssueDetails(owner, repo, issueId)
                    .execute().body() ?: return@async Result.error(IssueErrors.ISSUE_NOT_LOADED)

                return@async Result.success(issue)
            }

        asyncOperation.postOnMainThread {
            if (it.isError) {
                viewState.value = IssueDetailsMigrationViewState.Error(it.errorResult)
            } else {
                viewState.value = IssueDetailsMigrationViewState.IssueLoaded(it.successResult)
                loadComments(owner, repo, issueId)
            }
        }
    }

    private fun loadComments(owner: String, repo: String, issueId: Int) {
        multithreading.async<Result<List<CommentModel>, IssueErrors>> {

            val comments = repositoryService.getRepoIssueComments(owner, repo, issueId)
                .execute().body() ?: return@async Result.error(IssueErrors.COMMENTS_NOT_LOADED)

            return@async Result.success(comments)
        }.postOnMainThread {
            viewState.value = if (it.isError) IssueDetailsMigrationViewState.Error(it.errorResult)
            else IssueDetailsMigrationViewState.CommentsLoaded(it.successResult)
        }

    }

    fun loadCommentReactions(owner: String, repo: String, commentId: Int) {
        val asyncOperation =
            multithreading.async<Result<List<ReactionData>, IssueErrors>> {

                val reactions = repositoryService.getCommentReactions(owner, repo, commentId)
                    .execute().body() ?: return@async Result.error(IssueErrors.REACTIONS_NOT_LOADED)

                return@async Result.success(reactions)
            }

        asyncOperation
            .map(reactionsResultMapper::map)
            .postOnMainThread {
                val data = if (it.isError) ReactionDialogViewState.Error(it.errorResult)
                else ReactionDialogViewState.Ready(it.successResult)
                dialogEvent.value = Event(data)
            }
    }
}

sealed class ReactionDialogViewState {
    data class Ready(val data: List<ReactionType>) : ReactionDialogViewState()
    data class Error(val error: IssueErrors) : ReactionDialogViewState()
}

sealed class IssueDetailsMigrationViewState {
    object Loading : IssueDetailsMigrationViewState()
    data class IssueLoaded(val data: IssueDetailsMigrationModel): IssueDetailsMigrationViewState()
    data class CommentsLoaded(val data: List<CommentModel>) : IssueDetailsMigrationViewState()
    data class Error(val error: IssueErrors) : IssueDetailsMigrationViewState()
}