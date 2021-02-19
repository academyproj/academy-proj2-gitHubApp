package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.search.data.api.SearchUsersService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SearchModule {

    @Provides
    @Singleton
    fun provideUsersSearchApi(retrofit: Retrofit): SearchUsersService {
        return retrofit.create(SearchUsersService::class.java)
    }
}