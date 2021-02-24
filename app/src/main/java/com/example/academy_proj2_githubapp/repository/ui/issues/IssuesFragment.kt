package com.example.academy_proj2_githubapp.repository.ui.issues

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.IssuesFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import javax.inject.Inject

class IssuesFragment : BaseFragment() {

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

    override val isSearchButtonVisible: Boolean = true

    lateinit var binding: IssuesFragmentBinding
    @Inject
    lateinit var viewModel: IssuesViewModel
    private lateinit var issuesRVAdapter: IssuesRVAdapter

    private var owner: String = ""
    private var repo: String = ""

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

        arguments?.let {
            owner = it.getString(KEY_OWNER, "")
            repo = it.getString(KEY_REPO, "")
        }
        viewModel.getIssues(owner, repo)
    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, ::showResults)
    }

    private fun setupRv() {
        issuesRVAdapter = IssuesRVAdapter(::openIssueDetails)
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

    private fun openIssueDetails(callback: IssueCallbackModel) {
        navigator.openIssueDetails(owner, repo, callback.issue)
    }

}