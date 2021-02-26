package com.example.academy_proj2_githubapp.search.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.SearchFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import kotlinx.coroutines.*
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

        if (binding.etSearch.requestFocus()) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onDetach() {
        super.onDetach()
        coroutineScope.coroutineContext.cancel()
    }

    private val coroutineScope = CoroutineScope(Job())

    private fun setupListener() {
        val watcher = object : TextWatcher {
            var searchFor = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText

                coroutineScope.launch {
                    delay(300)
                    if (searchText != searchFor)
                        return@launch

                    if(searchText.isNotEmpty()) {
                        searchViewModel.searchUsers(searchText)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
        }
        binding.etSearch.addTextChangedListener(watcher)
    }

    private fun setupObserver() {
        searchViewModel.viewState.observe(viewLifecycleOwner, ::showSearchResults)
    }

    private fun setupRv() {
        searchAdapter = SearchAdapter(navigator::openProfileFragment)
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