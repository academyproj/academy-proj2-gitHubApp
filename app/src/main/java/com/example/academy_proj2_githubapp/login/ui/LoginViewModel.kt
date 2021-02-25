package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.repository.data.models.UserModel
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val gitHubUtils: GitHubUtils,
    private val sharedPreferences: SharedPrefs
) : ViewModel() {

    val tokenStatus = MutableLiveData<LoginViewStatus>()

    fun checkForToken(activity: Activity) {
        if (sharedPreferences.token == "" || sharedPreferences.refreshToken == "") {
            tokenStatus.value = LoginViewStatus.EmptyToken
            loadToken(activity)
        } else {
            refreshToken()
        }
    }

    private fun loadToken(activity: Activity) {
        val code = gitHubUtils.getCodeFromUri(activity.intent.data)
        code ?: return

        tokenStatus.value = LoginViewStatus.LoadingToken
        GlobalScope.launch {
            val response = gitHubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token
            sharedPreferences.refreshToken = response.refreshToken
            val user = gitHubUtils.getUser()

            Log.d("TAG", "token ${sharedPreferences.token}")

            tokenStatus.postValue(LoginViewStatus.LoadedToken(user))
        }
    }

    private fun refreshToken() {
        tokenStatus.value = LoginViewStatus.LoadingToken
        GlobalScope.launch {
            val response = gitHubUtils.refreshToken(sharedPreferences.refreshToken)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token
            sharedPreferences.refreshToken = response.refreshToken
            val user = gitHubUtils.getUser()
            sharedPreferences.userLogin = user.login
            Log.d("TAG", "token ${sharedPreferences.token}")

            tokenStatus.postValue(LoginViewStatus.LoadedToken(user))
        }
    }


    fun startLogin(activity: Activity) {
        val authIntent = Intent(Intent.ACTION_VIEW, gitHubUtils.buildAuthGitHubUrl())
        activity.startActivity(authIntent)
    }


    //TODO fix or delete
    fun clearCookies() {
        sharedPreferences.token = ""
        sharedPreferences.refreshToken = ""
        CookieManager.getInstance().setCookie("https://github.com", " ")

        CookieManager.getInstance().flush()
        tokenStatus.postValue(LoginViewStatus.EmptyToken)
    }
}

enum class LoginStatus {
    EMPTY,
    LOADING,
    LOADED
}


sealed class LoginViewStatus {
    object EmptyToken : LoginViewStatus()
    object LoadingToken : LoginViewStatus()
    data class LoadedToken(val user: UserModel) : LoginViewStatus()
}