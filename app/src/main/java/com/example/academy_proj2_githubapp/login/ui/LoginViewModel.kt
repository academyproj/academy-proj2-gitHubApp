package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TokenStatus {
    EMPTY,
    LOADING,
    LOADED
}

class LoginViewModel @Inject constructor(
    private val gitHubUtils: GitHubUtils,
    private val sharedPreferences: SharedPrefs
) : ViewModel() {

    val tokenStatus = MutableLiveData<TokenStatus>()

    fun checkForToken(activity: Activity) {
        if (sharedPreferences.token == "") {
            tokenStatus.postValue(TokenStatus.EMPTY)
            getToken(activity)
        } else {
            Log.d("TAG", sharedPreferences.token)
            tokenStatus.postValue(TokenStatus.LOADED)
        }
    }

    private fun getToken(activity: Activity) {
        val code = gitHubUtils.getCodeFromUri(activity.intent.data)
        code ?: return

        tokenStatus.postValue(TokenStatus.LOADING)
        GlobalScope.launch {
            val response = gitHubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token
            val user = gitHubUtils.getUser(token)

            Log.d("TAG", "token $token")
            Log.d("TAG", "user ${user.login}")
            tokenStatus.postValue(TokenStatus.LOADED)
        }
    }

    fun startLogin(activity: Activity) {
        val authIntent = Intent(Intent.ACTION_VIEW, gitHubUtils.buildAuthGitHubUrl())
        activity.startActivity(authIntent)
    }

}