package com.example.academy_proj2_githubapp.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.SearchFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override val isSearchButtonVisible: Boolean = false

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupObserver()
        setupListener()
    }

    private fun setupListener() {
        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (v != null && actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.etSearch.text?.let {
                    searchViewModel.searchUsers(it.toString())
                }
            }
            false
        }
    }

    private fun setupObserver() {
        searchViewModel.viewState.observe(viewLifecycleOwner, ::showSearchResults)
    }

    private fun setupRv() {
        searchAdapter = SearchAdapter {
            navigator.openProfileFragment(UserToLoad.CustomUser(it))
        }
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    private fun showSearchResults(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.SearchLoading -> {
                binding.pbSearchLoading.visibility = View.VISIBLE
            }
            is SearchViewState.SearchFailed -> {
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
            is SearchViewState.SearchSuccess -> {
                binding.pbSearchLoading.visibility = View.GONE
                searchAdapter.submitList(viewState.data)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}