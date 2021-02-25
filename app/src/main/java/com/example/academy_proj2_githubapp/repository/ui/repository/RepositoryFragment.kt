package com.example.academy_proj2_githubapp.repository.ui.repository

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.RepositoryFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import javax.inject.Inject

class RepositoryFragment : BaseFragment() {

    companion object {
        private const val KEY_USER = "KEY_USER"
        private const val KEY_REPO = "KEY_REPO"

        fun newInstance(user: String, repo: String): RepositoryFragment {
            val args = Bundle().apply {
                putString(KEY_USER, user)
                putString(KEY_REPO, repo)
            }

            val fragment = RepositoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModel: RepositoryViewModel

    lateinit var binding: RepositoryFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepositoryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        val repo = arguments?.getString(KEY_REPO)
        val owner = arguments?.getString(KEY_USER)

        repo?.let {
            owner?.let {
                viewModel.loadRepo(repo = repo, owner = owner)
                setupListeners(repo = repo, owner = owner)
            }
        }
    }

    override val isSearchButtonVisible: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }


    private fun setupObserver() {
        viewModel.repoState.observe(viewLifecycleOwner, ::updateUI)
    }

    private fun setupListeners(repo: String, owner: String) {
        binding.apply {
            llContributors.setOnClickListener {
                navigator.openContributorsFragment(repo = repo, user = owner)
            }
            llIssues.setOnClickListener {
                navigator.openIssuesFragment(repo = repo, owner = owner)
            }
        }
    }

    private fun updateUI(repoState: RepoState) {
        when (repoState) {
            is RepoState.RepoLoading -> {
                binding.apply {
                    pbRepository.visibility = View.VISIBLE
                }
            }
            is RepoState.RepoLoaded -> {
                binding.apply {
                    pbRepository.visibility = View.GONE

                    tvRepoName.text = repoState.data.repoName
                    tvRepoUserName.text = repoState.data.ownerName
                    tvRepoIssuesCount.text = repoState.data.issuesCount
                    tvRepoReadme.text = repoState.data.readme
                    tvRepoStarCount.text = repoState.data.starsCount
                    tvRepoForkCount.text = repoState.data.forksCount
                    tvRepoDescription.text = repoState.data.repoDescription

                    Glide.with(ivRepoUserIcon)
                        .load(repoState.data.ownerIconUrl)
                        .circleCrop()
                        .into(ivRepoUserIcon)

                }
            }
            is RepoState.RepoError -> {
                binding.apply {
                    pbRepository.visibility = View.GONE
                    tvRepoError.visibility = View.VISIBLE
                    tvRepoError.text = repoState.error
                }
            }
        }
    }
}