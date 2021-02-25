package com.example.academy_proj2_githubapp.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.login.ui.LoginFragment
import com.example.academy_proj2_githubapp.reactions.ReactionPickerDialog
import com.example.academy_proj2_githubapp.reactions.models.ReactionType
import com.example.academy_proj2_githubapp.repository.ui.contributors.ContributorsFragment
import com.example.academy_proj2_githubapp.repository.ui.issues.IssueDetailsFragmentMigration
import com.example.academy_proj2_githubapp.repository.ui.issues.IssuesFragment
import com.example.academy_proj2_githubapp.repository.ui.repository.RepositoryFragment
import com.example.academy_proj2_githubapp.search.ui.SearchFragment
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad
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

    fun openProfileFragment(user: UserToLoad) {
        fragmentManager.beginTransaction()
            .replace(containerId, UserProfileFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
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
            .replace(
                R.id.flFragmentContainer,
                ContributorsFragment.newInstance(repo = repo, owner = user)
            )
            .addToBackStack(null)
            .commit()
    }

    fun openIssuesFragment(repo: String, owner: String) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.flFragmentContainer,
                IssuesFragment.newInstance(repo = repo, owner = owner)
            )
            .addToBackStack(null)
            .commit()
    }

    fun openReactionsDialog(
        chosenReactions: List<ReactionType>,
        onReactionSelected: (ReactionType) -> Unit
    ) {
        ReactionPickerDialog.newInstance(chosenReactions, onReactionSelected)
            .show(fragmentManager, ReactionPickerDialog.REACTION_PICKER_TAG)
    }

    fun openIssueDetails(owner: String, repo: String, issueId: Int) {
        fragmentManager.beginTransaction()
            .replace(containerId, IssueDetailsFragmentMigration.newInstance(owner, repo, issueId))
            .commit()
    }

}