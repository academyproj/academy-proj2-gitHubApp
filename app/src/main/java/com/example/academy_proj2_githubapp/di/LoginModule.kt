package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.login.data.LoginService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginServiceApi(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubLoginUtils(loginService: LoginService): GitHubUtils {
        return GitHubUtils(loginService)
    }

}