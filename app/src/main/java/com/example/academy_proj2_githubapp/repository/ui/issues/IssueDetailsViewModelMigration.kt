package com.example.academy_proj2_githubapp.repository.ui.issues

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.reactions.models.ReactionModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionType
import com.example.academy_proj2_githubapp.repository.data.ReactionContent
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

    private var currentOwner: String = ""
    private var currentRepo: String = ""
    private var currentIssue: Int = -1
    private var currentComment: Int = -1
    private var currentActiveReactions: List<ReactionModel> = emptyList()

    fun loadIssue(owner: String, repo: String, issueId: Int) {
        viewState.value = IssueDetailsMigrationViewState.Loading
        currentOwner = owner
        currentRepo = repo
        currentIssue = issueId
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
                loadComments()
            }
        }
    }

    private fun loadComments() {
        multithreading.async<Result<List<CommentModel>, IssueErrors>> {

            val comments =
                repositoryService.getRepoIssueComments(currentOwner, currentRepo, currentIssue)
                    .execute().body() ?: return@async Result.error(IssueErrors.COMMENTS_NOT_LOADED)

            return@async Result.success(comments)
        }.postOnMainThread {
            viewState.value = if (it.isError) IssueDetailsMigrationViewState.Error(it.errorResult)
            else IssueDetailsMigrationViewState.CommentsLoaded(it.successResult)
        }

    }

    fun loadCommentReactions(commentId: Int) {
        dialogEvent.value = Event(ReactionDialogViewState.Loading)
        currentComment = commentId
        val asyncOperation =
            multithreading.async<Result<List<ReactionData>, IssueErrors>> {

                val reactions =
                    repositoryService.getCommentReactions(currentOwner, currentRepo, commentId)
                        .execute().body()
                        ?: return@async Result.error(IssueErrors.REACTIONS_NOT_LOADED)

                return@async Result.success(reactions)
            }

        asyncOperation
            .map(reactionsResultMapper::map)
            .postOnMainThread { result ->
                val data = if (result.isError) {
                    ReactionDialogViewState.Error(result.errorResult)
                } else {
                    currentActiveReactions = result.successResult
                    ReactionDialogViewState.Ready(currentActiveReactions.map { it.type })
                }
                dialogEvent.value = Event(data)
            }
    }

    private fun createReaction(reaction: ReactionType) {
        multithreading.async {
            repositoryService.createIssueCommentReaction(
                currentOwner,
                currentRepo,
                currentComment,
                ReactionContent(reaction.content)
            ).execute()
        }.postOnMainThread {
            loadComments()
        }
    }

    fun onReactionChosen(reaction: ReactionType) {
        val reactionId = currentActiveReactions
            .firstOrNull { it.type == reaction }?.id

        reactionId?.let {
            deleteReactionById(it)
            return
        }

        createReaction(reaction)
    }

    private fun deleteReactionById(reactionId: Int) {
        multithreading.async {
            repositoryService.deleteCommentReaction(
                currentOwner,
                currentRepo,
                currentComment,
                reactionId
            ).execute()
        }.postOnMainThread {
            loadComments()
        }
    }
}

sealed class ReactionDialogViewState {
    object Loading : ReactionDialogViewState()
    data class Ready(val data: List<ReactionType>) : ReactionDialogViewState()
    data class Error(val error: IssueErrors) : ReactionDialogViewState()
}

sealed class IssueDetailsMigrationViewState {
    object Loading : IssueDetailsMigrationViewState()
    data class IssueLoaded(val data: IssueDetailsMigrationModel) : IssueDetailsMigrationViewState()
    data class CommentsLoaded(val data: List<CommentModel>) : IssueDetailsMigrationViewState()
    data class Error(val error: IssueErrors) : IssueDetailsMigrationViewState()
}