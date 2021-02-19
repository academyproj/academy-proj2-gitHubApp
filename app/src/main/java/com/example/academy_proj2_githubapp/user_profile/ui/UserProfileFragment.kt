package com.example.academy_proj2_githubapp.user_profile.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.UserProfileFragmentBinding
import javax.inject.Inject

private const val USERNAME_PARAM = "USERNAME_PARAM"

class UserProfileFragment : Fragment() {

    companion object {
        fun newInstance(username: String): UserProfileFragment {
            val args = Bundle().apply {
                putString(USERNAME_PARAM, username)
            }
            return UserProfileFragment().apply {
                arguments = args
            }
        }
    }

    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var userProfileViewModel: UserProfileViewModel

    private lateinit var reposAdapter: ReposAdapter
    private var username: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME_PARAM)
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
        username?.let {
            userProfileViewModel.loadUserInfo(it)
        }
    }

    private fun setupRv() {
        reposAdapter = ReposAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}