package com.example.academy_proj2_githubapp.login.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.LoginFragmentBinding
import com.example.academy_proj2_githubapp.navigation.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.checkForToken(requireActivity())
    }

    private fun setupListeners() {
        binding.btLogin.setOnClickListener {
            loginViewModel.startLogin(requireActivity())
        }
        binding.btLoginContinue.setOnClickListener {
            navigator.openProfileFragment(null)
        }
    }

    private fun setupObserver() {
        loginViewModel.tokenStatus.observe(viewLifecycleOwner, ::updateUI)
    }

    private fun updateUI(tokenStatus: TokenStatus) {
        when (tokenStatus) {
            TokenStatus.EMPTY -> {
                binding.apply {
                    pbLogin.visibility = View.GONE
                    btLogin.visibility = View.VISIBLE
                    btLoginContinue.visibility = View.GONE
                }
            }
            TokenStatus.LOADED -> {
                binding.apply {
                    pbLogin.visibility = View.GONE
                    btLogin.visibility = View.GONE
                    btLoginContinue.visibility = View.VISIBLE
                }
            }
            TokenStatus.LOADING -> {
                binding.apply {
                    pbLogin.visibility = View.VISIBLE
                    btLoginContinue.visibility = View.GONE
                    btLogin.visibility = View.GONE
                }
            }
        }
    }
}