package com.example.academy_proj2_githubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_proj2_githubapp.databinding.NavigationActivityBinding
import com.example.academy_proj2_githubapp.navigation.Navigator
import com.example.academy_proj2_githubapp.search.ui.SearchFragment

class NavigationActivity : AppCompatActivity() {

    val navigator by lazy { Navigator(supportFragmentManager, R.id.flFragmentContainer) }

    private lateinit var binding: NavigationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupSearchButton()
    }

    private fun setupBinding() {
        binding = NavigationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigator.openLoginFragment()
    }


    // TODO replace button with status bar
    private fun setupSearchButton() {
        binding.fbOpenSearch.setOnClickListener {
            navigator.openSearchFragment()
        }
    }

}