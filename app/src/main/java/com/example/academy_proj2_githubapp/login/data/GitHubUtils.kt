package com.example.academy_proj2_githubapp.login.data

import android.net.Uri
import android.util.Log
import com.example.academy_proj2_githubapp.login.data.models.AccessToken
import com.example.academy_proj2_githubapp.login.data.models.User
import com.example.academy_proj2_githubapp.repository.data.models.UserModel
import javax.inject.Inject

class GitHubUtils @Inject constructor(
    private val loginService: LoginService,
    private val userService: UserService
) {

    private companion object {
        const val clientId = "81e826e2d9913602fa7a"
        const val clientSecret = "6229190ecdc9fed61890e17e72d5d2c295408d96"
        const val redirectUrl = "academyproj2://callback"
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

    suspend fun getUser(): UserModel {
        return userService.getUser()
    }

    suspend fun refreshToken(refreshToken: String): AccessToken {
        return loginService.refreshToken(refreshToken, "refresh_token", clientId, clientSecret)
    }

}