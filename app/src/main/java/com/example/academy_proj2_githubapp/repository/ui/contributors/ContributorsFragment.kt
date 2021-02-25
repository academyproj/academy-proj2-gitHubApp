package com.example.academy_proj2_githubapp.repository.ui.contributors

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.ContributorsFragmentBinding
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad
import com.example.academy_proj2_githubapp.user_profile.ui.UserProfileFragment
import javax.inject.Inject

class ContributorsFragment : Fragment() {

    companion object {
        private const val KEY_OWNER = "KEY_OWNER"
        private const val KEY_REPO = "KEY_REPO"

        fun newInstance(owner: String, repo: String): ContributorsFragment {
            val bundle = Bundle().apply {
                putString(KEY_OWNER, owner)
                putString(KEY_REPO, repo)
            }

            val fragment = ContributorsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var binding: ContributorsFragmentBinding
    @Inject
    lateinit var viewModel: ContributorsViewModel
    private lateinit var contributorsAdapter: ContributorsRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContributorsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupObserver()

        arguments?.getString(KEY_OWNER, "")?.let { owner ->
            arguments?.getString(KEY_REPO, "")?.let { repo ->
                viewModel.getContributors(owner, repo)
            }
        }
    }

    private fun setupObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, ::showSearchResults)
    }

    private fun setupRv() {
        contributorsAdapter = ContributorsRVAdapter(::test)
        binding.rvContributorsResults.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            adapter = contributorsAdapter
        }
    }

    private fun showSearchResults(viewState: ContributorsViewState) {
        when (viewState) {
            is ContributorsViewState.ContributorsLoading -> {
                binding.pbContributorsLoading.visibility = View.VISIBLE
            }
            is ContributorsViewState.ContributorsFailed -> {
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
            is ContributorsViewState.ContributorsSuccess -> {
                binding.pbContributorsLoading.visibility = View.GONE
                contributorsAdapter.submitList(viewState.data)
            }

        }
    }


    //TODO delete swap to issue details
    private fun test(username: String) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flFragmentContainer, UserProfileFragment.newInstance(UserToLoad.CustomUser(username)))
            ?.addToBackStack(null)?.commit()
    }
}