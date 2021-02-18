package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.login.ui.LoginFragment
import com.example.academy_proj2_githubapp.search.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SearchModule::class, LoginModule::class])
interface AppComponent {

    fun inject(fragment: SearchFragment)

    fun inject(fragment: LoginFragment)


}