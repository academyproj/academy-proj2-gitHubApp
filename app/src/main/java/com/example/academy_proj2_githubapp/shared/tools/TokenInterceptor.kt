package com.example.academy_proj2_githubapp.shared.tools

import android.accounts.AccountManager
import android.util.Log
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http2.Header
import javax.inject.Inject

//TODO token header
class TokenInterceptor @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if(sharedPrefs.token != "") {
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json")
                        .addHeader("Authorization", sharedPrefs.token)
                        .build()
                )
            }
        } else {
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json")
                        .build()
                )
            }
        }
    }
}