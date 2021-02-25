package com.example.academy_proj2_githubapp.user_profile.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.UserProfileFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad
import javax.inject.Inject

private const val USER_PARAM = "USER_PARAM"

class UserProfileFragment : BaseFragment() {

    companion object {
        fun newInstance(userToLoad: UserToLoad): UserProfileFragment {
            val args = Bundle().apply {
                putSerializable(USER_PARAM, userToLoad)
            }
            return UserProfileFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override val isSearchButtonVisible: Boolean = true

    @Inject
    lateinit var userProfileViewModel: UserProfileViewModel

    private lateinit var reposAdapter: ReposAdapter
    private var currentUser: UserToLoad? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentUser = it.getSerializable(USER_PARAM) as UserToLoad
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserProfileFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupObserver()
        currentUser?.let {
            userProfileViewModel.loadUserInfo(it)
        }
    }

    private fun setupRv() {
        reposAdapter = ReposAdapter(::showRepo)
        binding.rvUserRepos.apply {
            adapter = reposAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupObserver() {
        userProfileViewModel.viewState.observe(viewLifecycleOwner, ::updateUi)
    }

    private fun updateUi(viewState: UserInfoViewState) {
        when (viewState) {
            is UserInfoViewState.Loading -> {
                binding.pbUserInfoLoading.visibility = View.VISIBLE
            }
            is UserInfoViewState.UserInfoLoaded -> {
                binding.apply {
                    tvUserProfileName.text = viewState.data.name
                    tvUserProfileLogin.text = viewState.data.login
                    tvUserProfileLocation.text = viewState.data.location
                    tvUserProfileJob.text = viewState.data.company
                    tvUserProfileFollow.text = viewState.data.follow

                    if (viewState.data.bio != "") {
                        tvUserProfileDescription.text = viewState.data.bio
                    } else {
                        tvUserProfileDescription.visibility = View.GONE
                    }
                }
                Glide.with(binding.ivUserProfileAvatar)
                    .load(viewState.data.avatarUrl)
                    .circleCrop()
                    .into(binding.ivUserProfileAvatar)
            }
            is UserInfoViewState.ReposInfoLoaded -> {
                binding.pbUserInfoLoading.visibility = View.GONE
                reposAdapter.submitList(viewState.data)
            }
            is UserInfoViewState.Error -> {
                binding.pbUserInfoLoading.visibility = View.GONE
                Toast.makeText(context, viewState.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showRepo(callback: RepoCallback) {
        navigator.openRepoFragment(callback.owner, callback.repo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}