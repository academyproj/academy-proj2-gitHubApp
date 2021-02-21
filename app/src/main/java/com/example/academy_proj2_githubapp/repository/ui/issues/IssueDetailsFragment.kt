package com.example.academy_proj2_githubapp.repository.ui.issues

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.IssueDetailsFragmentBinding
import javax.inject.Inject

class IssueDetailsFragment : Fragment() {

    companion object {
        private const val KEY_OWNER = "KEY_OWNER"
        private const val KEY_REPO = "KEY_REPO"
        private const val KEY_ISSUE = "KEY_ISSUE"

        fun newInstance(owner: String, repo: String, issue: Int): IssueDetailsFragment {
            val bundle = Bundle().apply {
                putString(KEY_OWNER, owner)
                putString(KEY_REPO, repo)
                putInt(KEY_ISSUE, issue)
            }

            val fragment = IssueDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var binding: IssueDetailsFragmentBinding
    @Inject
    lateinit var viewModel: IssueDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssueDetailsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()

        arguments?.getString(KEY_OWNER, "")?.let { owner ->
            arguments?.getString(KEY_REPO, "")?.let { repo ->
                arguments?.getInt(KEY_ISSUE)?.let { issue ->
                    viewModel.getIssues(owner, repo, issue)
                }
            }
        }

    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, ::showResults)
    }

    private fun showResults(viewState: IssueDetailsViewState) {
        when (viewState) {
            is IssueDetailsViewState.IssueDetailsLoading -> {
                binding.pbIssueDetails.visibility = View.VISIBLE
            }
            is IssueDetailsViewState.IssueDetailsFailed -> {
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
            is IssueDetailsViewState.IssueDetailsSuccess -> {
                binding.pbIssueDetails.visibility = View.GONE
                binding.apply {
                    tvIssueDetailsRepo.text = viewState.data.repoUrl.substringAfterLast("/")
                    tvIssueDetailsTitle.text = viewState.data.title
                    tvIssueDetailsBody.text = viewState.data.body
                    tvIssueDetailsUserName.text = viewState.data.user.login

                    Glide.with(ivIssueDetails)
                        .load(viewState.data.user.avatarUrl)
                        .circleCrop()
                        .into(ivIssueDetails)
                }
                //issuesRVAdapter.submitList(viewState.data)
            }

        }
    }

}