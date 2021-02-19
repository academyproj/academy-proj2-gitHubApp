package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.user_profile.data.api.ReposListService
import com.example.academy_proj2_githubapp.user_profile.data.api.UsersService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UserProfileModule {

    @Provides
    @Singleton
    fun getUsersService(retrofit: Retrofit): UsersService =
        retrofit.create(UsersService::class.java)

    @Provides
    @Singleton
    fun getReposListService(retrofit: Retrofit): ReposListService =
        retrofit.create(ReposListService::class.java)

}