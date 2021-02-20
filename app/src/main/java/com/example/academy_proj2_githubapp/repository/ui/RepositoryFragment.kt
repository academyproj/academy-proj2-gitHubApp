package com.example.academy_proj2_githubapp.repository.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.RepositoryFragmentBinding
import com.example.academy_proj2_githubapp.databinding.UserProfileFragmentBinding
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
        viewModel.loadRepo("Alehandrissimus", "buryachenko-proj1-GameScoreApp")
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

            }
            llIssues.setOnClickListener {

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
                    Log.d("TAG", tvRepoReadme.text.toString())
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