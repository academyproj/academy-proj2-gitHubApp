package com.example.academy_proj2_githubapp.repository.ui.issues

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.IssuesFragmentBinding
import com.example.academy_proj2_githubapp.user_profile.ui.UserProfileFragment
import javax.inject.Inject

class IssuesFragment : Fragment() {

    companion object {
        private const val KEY_OWNER = "KEY_OWNER"
        private const val KEY_REPO = "KEY_REPO"

        fun newInstance(owner: String, repo: String): IssuesFragment {
            val bundle = Bundle().apply {
                putString(KEY_OWNER, owner)
                putString(KEY_REPO, repo)
            }

            val fragment = IssuesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var binding: IssuesFragmentBinding
    @Inject
    lateinit var viewModel: IssuesViewModel
    private lateinit var issuesRVAdapter: IssuesRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IssuesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupObserver()

        arguments?.getString(KEY_OWNER, "")?.let { owner ->
            arguments?.getString(KEY_REPO, "")?.let { repo ->
                viewModel.getIssues(owner, repo)
            }
        }
    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, ::showResults)
    }

    private fun setupRv() {
        issuesRVAdapter = IssuesRVAdapter(::test)
        binding.rvIssuesResults.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter = issuesRVAdapter
        }
    }

    private fun showResults(viewState: IssuesViewState) {
        when (viewState) {
            is IssuesViewState.IssuesLoading -> {
                binding.pbIssuesLoading.visibility = View.VISIBLE
            }
            is IssuesViewState.IssuesFailed -> {
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
            is IssuesViewState.IssuesSuccess -> {
                binding.pbIssuesLoading.visibility = View.GONE
                issuesRVAdapter.submitList(viewState.data)
            }
        }
    }

    //TODO navigator
    private fun test(issue: IssueCallbackModel) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flFragmentContainer, IssueDetailsFragment.newInstance(issue.owner, issue.repo, issue.issue))
            ?.addToBackStack(null)?.commit()
    }

}