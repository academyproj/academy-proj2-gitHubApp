package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.login.ui.LoginFragment
import com.example.academy_proj2_githubapp.repository.ui.contributors.ContributorsFragment
import com.example.academy_proj2_githubapp.repository.ui.issues.IssueDetailsFragment
import com.example.academy_proj2_githubapp.repository.ui.issues.IssueDetailsFragmentMigration
import com.example.academy_proj2_githubapp.repository.ui.issues.IssuesFragment
import com.example.academy_proj2_githubapp.repository.ui.repository.RepositoryFragment
import com.example.academy_proj2_githubapp.search.ui.SearchFragment
import com.example.academy_proj2_githubapp.user_profile.ui.UserProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SearchModule::class, LoginModule::class, UserProfileModule::class, RepoModule::class])
interface AppComponent {

    fun inject(fragment: SearchFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: UserProfileFragment)
    fun inject(fragment: RepositoryFragment)
    fun inject(fragment: ContributorsFragment)
    fun inject(fragment: IssuesFragment)
    fun inject(fragment: IssueDetailsFragment)
    fun inject(fragment: IssueDetailsFragmentMigration)
}