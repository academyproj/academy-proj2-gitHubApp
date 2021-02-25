package com.example.academy_proj2_githubapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_proj2_githubapp.databinding.NavigationActivityBinding
import com.example.academy_proj2_githubapp.navigation.Navigator

class NavigationActivity : AppCompatActivity() {

    private companion object {
        const val LOGGED_IN_KEY = "LOGGED_IN_KEY"
    }

    val navigator by lazy { Navigator(supportFragmentManager, R.id.flFragmentContainer) }

    private lateinit var binding: NavigationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupSearchButton()

        if (savedInstanceState?.getBoolean(LOGGED_IN_KEY) != true)
            navigator.openLoginFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(LOGGED_IN_KEY, true)
    }

    private fun setupBinding() {
        binding = NavigationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupSearchButton() {
        binding.fbOpenSearch.setOnClickListener {
            navigator.openSearchFragment()
        }
    }

    fun setSearchButtonVisibility(isVisible: Boolean) {
        binding.fbOpenSearch.visibility =
            if (isVisible) View.VISIBLE
            else View.GONE
    }

}