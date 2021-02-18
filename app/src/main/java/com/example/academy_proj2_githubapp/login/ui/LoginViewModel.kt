package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val gitHubUtils: GitHubUtils,
    private val sharedPreferences: SharedPrefs
) : ViewModel() {

    fun getToken(activity: Activity) {

        val code = gitHubUtils.getCodeFromUri(activity.intent.data)
        code ?: return

        Log.d("TAG", "getToken")
        GlobalScope.launch {
            val response = gitHubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token
            val user = gitHubUtils.getUser(token)

            Log.d("TAG", "token $token")
            Log.d("TAG", "user $user")
        }
    }

    fun startLogin(activity: Activity) {
        val authIntent = Intent(Intent.ACTION_VIEW, gitHubUtils.buildAuthGitHubUrl())
        activity.startActivity(authIntent)
    }

}