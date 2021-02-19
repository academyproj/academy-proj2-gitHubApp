package com.example.academy_proj2_githubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.academy_proj2_githubapp.databinding.ActivityMainBinding
import com.example.academy_proj2_githubapp.repository.ui.RepositoryFragment
import com.example.academy_proj2_githubapp.search.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repositoryTest()
//        tempTest()
    }

    // TODO delete this
    private fun tempTest() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, SearchFragment.newInstance())
            .commit()
    }


    private fun repositoryTest() {
        supportFragmentManager.beginTransaction()
            .add(R.id.flFragmentContainer, RepositoryFragment.newInstance())
            .commitAllowingStateLoss()
    }

}