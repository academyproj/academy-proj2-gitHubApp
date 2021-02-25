package com.example.academy_proj2_githubapp.di

import android.content.Context
import com.example.academy_proj2_githubapp.shared.network.GitHubHeadersInterceptor
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun getContext(): Context = context

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(headersInterceptor: GitHubHeadersInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(headersInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPrefs {
        return SharedPrefs(context)
    }
}