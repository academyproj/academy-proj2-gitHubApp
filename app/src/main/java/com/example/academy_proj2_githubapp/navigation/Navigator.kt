package com.example.academy_proj2_githubapp.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.academy_proj2_githubapp.login.ui.LoginFragment
import com.example.academy_proj2_githubapp.reactions.ReactionPickerDialog
import com.example.academy_proj2_githubapp.reactions.models.ReactionType
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

        // TODO Replace with sealed class
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

    fun openReactionsDialog(
        chosenReactions: List<ReactionType>,
        onReactionSelected: (ReactionType) -> Unit
    ) {
        ReactionPickerDialog.newInstance(chosenReactions, onReactionSelected)
            .show(fragmentManager, ReactionPickerDialog.REACTION_PICKER_TAG)
    }

}