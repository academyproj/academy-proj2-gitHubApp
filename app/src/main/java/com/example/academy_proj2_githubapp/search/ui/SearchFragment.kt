package com.example.academy_proj2_githubapp.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_proj2_githubapp.databinding.SearchFragmentBinding
import javax.inject.Inject

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter

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
        searchAdapter = SearchAdapter()
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter

            val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun showSearchResults(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.SearchLoading -> {
                binding.rvSearchResults.visibility = View.GONE
            }
            is SearchViewState.SearchFailed -> {
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
            is SearchViewState.SearchSuccess -> {
                binding.rvSearchResults.visibility = View.VISIBLE
                searchAdapter.submitList(viewState.data)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}