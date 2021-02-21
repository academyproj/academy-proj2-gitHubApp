package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.repository.data.RepositoryService
import com.example.academy_proj2_githubapp.repository.ui.contributors.ContributorsViewModel
import com.example.academy_proj2_githubapp.repository.ui.repository.RepositoryViewModel
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideRepositoryService(retrofit: Retrofit): RepositoryService {
        return retrofit.create(RepositoryService::class.java)
    }

    @Provides
    fun provideRepositoryViewModel(repositoryService: RepositoryService, multithreading: Multithreading): RepositoryViewModel {
        return RepositoryViewModel(repositoryService, multithreading)
    }

    @Provides
    fun provideContributorsViewModel(repositoryService: RepositoryService, multithreading: Multithreading): ContributorsViewModel {
        return ContributorsViewModel(repositoryService, multithreading)
    }
}