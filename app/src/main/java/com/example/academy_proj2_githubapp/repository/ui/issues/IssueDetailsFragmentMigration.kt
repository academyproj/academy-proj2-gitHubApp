package com.example.academy_proj2_githubapp.repository.ui.issues

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.IssueDetailsFragmentMigrationBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import com.example.academy_proj2_githubapp.repository.data.models.toCommentModel
import com.example.academy_proj2_githubapp.shared.utils.Event
import javax.inject.Inject

class IssueDetailsFragmentMigration : BaseFragment() {

    companion object {
        fun newInstance(owner: String, repo: String, issueId: Int): IssueDetailsFragmentMigration {
            return IssueDetailsFragmentMigration().apply {
                arguments = Bundle().apply {
                    putString(OWNER_TAG, owner)
                    putString(REPO_TAG, repo)
                    putInt(ID_TAG, issueId)
                }
            }
        }

        private const val OWNER_TAG = "owner"
        private const val REPO_TAG = "repo"
        private const val ID_TAG = "id"

    }

    override val isSearchButtonVisible = true

    @Inject
    lateinit var viewModel: IssueDetailsViewModelMigration

    private var owner: String = ""
    private var repo: String = ""
    private var issueId: Int = -1

    private var _binding: IssueDetailsFragmentMigrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var commentsAdapter: IssueCommentsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            owner = it.getString(OWNER_TAG, "")
            repo = it.getString(REPO_TAG, "")
            issueId = it.getInt(ID_TAG, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IssueDetailsFragmentMigrationBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        viewModel.viewState.observe(viewLifecycleOwner, ::onViewStateUpdate)
        viewModel.dialogEvent.observe(viewLifecycleOwner, ::onDialogUpdate)
        viewModel.loadIssue(owner, repo, issueId)
    }

    private fun onViewStateUpdate(viewState: IssueDetailsMigrationViewState) {
        when (viewState) {
            is IssueDetailsMigrationViewState.Loading -> {
                binding.pbIssueDetailsMigrationLoading.visibility = View.VISIBLE
            }
            is IssueDetailsMigrationViewState.IssueLoaded -> {
                binding.pbIssueDetailsMigrationLoading.visibility = View.GONE
                binding.tvIssueDetailsMigrationTitle.text = viewState.data.title
                val newList = listOf(viewState.data.toCommentModel())
                commentsAdapter.submitList(newList)
            }
            is IssueDetailsMigrationViewState.CommentsLoaded -> {
                val newList = listOf(commentsAdapter.currentList[0]) + viewState.data
                commentsAdapter.submitList(newList)
            }
            is IssueDetailsMigrationViewState.Error -> {
                binding.pbIssueDetailsMigrationLoading.visibility = View.GONE
            }
            // TODO do smth on error
        }
    }

    private fun onDialogUpdate(event: Event<ReactionDialogViewState>) {
        event.getContentIfNotHandled()?.let {
            when (it) {
                is ReactionDialogViewState.Ready -> {
                    binding.pbIssueDetailsMigrationLoading.visibility = View.GONE
                    navigator.openReactionsDialog(it.data) { reaction ->
                        viewModel.onReactionChosen(reaction)
                    }
                }
                is ReactionDialogViewState.Error -> {
                    binding.pbIssueDetailsMigrationLoading.visibility = View.GONE
                }
                is ReactionDialogViewState.Loading -> {
                    binding.pbIssueDetailsMigrationLoading.visibility = View.VISIBLE
                }
                // TODO do smth on error
            }
        }
    }

    private fun setupRv() {
        commentsAdapter = IssueCommentsAdapter { commentId ->
            viewModel.loadCommentReactions(commentId)
        }
        binding.rvIssueComments.apply {
            adapter = commentsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}