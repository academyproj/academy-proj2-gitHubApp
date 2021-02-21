package com.example.academy_proj2_githubapp.shared.network

import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GitHubHeadersInterceptor @Inject constructor(private val sharedPrefs: SharedPrefs): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Authorization", sharedPrefs.token)
                .build()
        )
    }
}