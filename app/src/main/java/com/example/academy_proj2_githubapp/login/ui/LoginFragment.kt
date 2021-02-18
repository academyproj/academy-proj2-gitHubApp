package com.example.academy_proj2_githubapp.login.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academy_proj2_githubapp.AppApplication
import com.example.academy_proj2_githubapp.databinding.LoginFragmentBinding
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding

    @Inject
    lateinit var viewModel: LoginViewModel

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.getToken(requireActivity())
    }

    private fun setupListeners() {
        binding.btLogin.setOnClickListener {
            viewModel.startLogin(requireActivity())
        }
    }

}