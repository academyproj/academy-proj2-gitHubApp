package com.example.academy_proj2_githubapp.login.data

import android.net.Uri
import android.util.Log
import com.example.academy_proj2_githubapp.login.data.models.AccessToken
import com.example.academy_proj2_githubapp.login.data.models.User
import javax.inject.Inject

class GitHubUtils @Inject constructor(
    private val loginService: LoginService,
    private val userService: UserService
) {

    private companion object {
        const val clientId = "Iv1.285bc9168541e271"
        const val clientSecret = "22b361ae6e2b0e23ec3edbb4e39eb89bceedce63"
        const val redirectUrl = "academy-proj2-githubapp://callback"
        const val scopes = "repo, user"
        const val schema = "https"
        const val host = "github.com"
    }

    fun buildAuthGitHubUrl(): Uri {

        return Uri.Builder()
            .scheme(schema)
            .authority(host)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    suspend fun getAccessToken(code: String): AccessToken {
        return loginService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(token: String): User {
        return userService.getUser(token)
    }

}