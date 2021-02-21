package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.user_profile.data.api.UserInfoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UserProfileModule {

    @Provides
    @Singleton
    fun getUsersService(retrofit: Retrofit): UserInfoService =
        retrofit.create(UserInfoService::class.java)

}