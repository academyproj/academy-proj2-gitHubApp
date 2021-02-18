package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.search.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SearchModule::class])
interface AppComponent {

    fun inject(fragment: SearchFragment)
}