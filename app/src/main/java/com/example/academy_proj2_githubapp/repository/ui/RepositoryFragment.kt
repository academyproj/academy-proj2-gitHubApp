package com.example.academy_proj2_githubapp.repository.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        setupObserver()
        viewModel.loadRepo("Alehandrissimus", "buryachenko-HW21-Arch1")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }


    private fun setupObserver() {
        viewModel.repoState.observe(viewLifecycleOwner, ::updateUI)
    }

    private fun updateUI(repoState: RepoState) {
        when(repoState) {
            is RepoState.RepoLoading -> {
                binding.apply {
                    pbRepository.visibility = View.VISIBLE
                    tvRepository.visibility = View.GONE
                }
            }
            is RepoState.RepoLoaded -> {
                binding.apply {
                    pbRepository.visibility = View.GONE
                    tvRepository.visibility = View.VISIBLE
                    tvRepository.text = viewModel.repoState.value.toString()
                }
            }
            is RepoState.RepoError -> {
                binding.apply {
                    pbRepository.visibility = View.GONE
                    tvRepository.visibility = View.VISIBLE
                    tvRepository.text = viewModel.repoState.value.toString()
                }
            }
        }
    }
}