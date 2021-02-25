package com.example.academy_proj2_githubapp.di

import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.login.data.LoginService
import com.example.academy_proj2_githubapp.login.data.UserService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginServiceApi(
        @Named("githubLogin") retrofit: Retrofit
    ): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubLoginUtils(loginService: LoginService, userService: UserService): GitHubUtils {
        return GitHubUtils(loginService, userService)
    }

    @Provides
    @Singleton
    @Named("githubLogin")
    fun provideGitHubRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl("https://github.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}