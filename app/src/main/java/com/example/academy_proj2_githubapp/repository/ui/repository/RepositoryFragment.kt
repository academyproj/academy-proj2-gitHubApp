package com.example.academy_proj2_githubapp.repository.ui.repository

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.RepositoryFragmentBinding
import com.example.academy_proj2_githubapp.repository.ui.contributors.ContributorsFragment
import com.example.academy_proj2_githubapp.repository.ui.issues.IssuesFragment
import javax.inject.Inject

class RepositoryFragment : Fragment() {

    companion object {
        fun newInstance(): RepositoryFragment {
            val args = Bundle()

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
        setupListeners()
        setupObserver()

        //TODO delete
        viewModel.loadRepo("Alehandrissimus", "buryachenko-proj1-gamescoreapp")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }


    private fun setupObserver() {
        viewModel.repoState.observe(viewLifecycleOwner, ::updateUI)
    }

    private fun setupListeners() {
        binding.apply {
            llContributors.setOnClickListener {
                //TODO navigator
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.flFragmentContainer, ContributorsFragment.newInstance("Alehandrissimus", "buryachenko-proj1-gamescoreapp"))
                    ?.addToBackStack(null)?.commit()
            }
            llIssues.setOnClickListener {
                //TODO navigator
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.flFragmentContainer, IssuesFragment.newInstance("Alehandrissimus", "buryachenko-proj1-gamescoreapp"))
                    ?.addToBackStack(null)?.commit()

            }
        }
    }

    private fun updateUI(repoState: RepoState) {
        when(repoState) {
            is RepoState.RepoLoading -> {
                binding.apply {
                    pbRepository.visibility = View.VISIBLE
                }
            }
            is RepoState.RepoLoaded -> {
                binding.apply {
                    pbRepository.visibility = View.GONE

                    tvRepoName.text = repoState.data.name
                    tvRepoUserName.text = repoState.data.owner.login
                    tvRepoIssuesCount.text = repoState.data.openIssuesCount.toString()
                    tvRepoReadme.text = repoState.data.readme

                    Glide.with(ivRepoUserIcon)
                        .load(repoState.data.owner.avatarUrl)
                        .circleCrop()
                        .into(ivRepoUserIcon)

                    if(repoState.data.description != null) {
                        tvRepoDescription.text = repoState.data.description
                    } else {
                        tvRepoDescription.visibility = View.GONE
                    }
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