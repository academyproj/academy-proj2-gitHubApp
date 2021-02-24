package com.example.academy_proj2_githubapp.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.login.ui.LoginFragment
import com.example.academy_proj2_githubapp.repository.ui.contributors.ContributorsFragment
import com.example.academy_proj2_githubapp.repository.ui.issues.IssuesFragment
import com.example.academy_proj2_githubapp.repository.ui.repository.RepositoryFragment
import com.example.academy_proj2_githubapp.search.ui.SearchFragment
import com.example.academy_proj2_githubapp.user_profile.ui.UserProfileFragment

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerId: Int,
) {

    fun openLoginFragment() {
        fragmentManager.beginTransaction()
            .replace(containerId, LoginFragment.newInstance())
            .commit()
    }

    fun openProfileFragment(userName: String?) {
        val transaction = fragmentManager.beginTransaction()
            .replace(containerId, UserProfileFragment.newInstance(userName))

        // Don't add login fragment to BackStack
        userName?.let { transaction.addToBackStack(null) }
        transaction.commit()
    }

    fun openSearchFragment() {
        fragmentManager.beginTransaction()
            .replace(containerId, SearchFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun openRepoFragment(user: String, repo: String) {
        fragmentManager.beginTransaction()
            .replace(containerId, RepositoryFragment.newInstance(user, repo))
            .addToBackStack(null)
            .commit()
    }

    fun openContributorsFragment(user: String, repo: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, ContributorsFragment.newInstance(repo = repo, owner = user))
            .addToBackStack(null)
            .commit()
    }

    fun openIssuesFragment(repo: String, owner: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.flFragmentContainer, IssuesFragment.newInstance(repo = repo, owner = owner))
            .addToBackStack(null)
            .commit()
    }



}