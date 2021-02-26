package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val gitHubUtils: GitHubUtils,
    private val sharedPreferences: SharedPrefs
) : ViewModel() {

    val tokenStatus = MutableLiveData<LoginViewStatus>()

    fun checkForToken(activity: Activity) {
        if (sharedPreferences.token == "") {
            tokenStatus.value = LoginViewStatus.EmptyToken
            loadToken(activity)
        } else {
            loadUser()
        }
    }

    private val coroutineScope = CoroutineScope(Job())

    private fun loadToken(activity: Activity) {
        val code = gitHubUtils.getCodeFromUri(activity.intent.data)
        code ?: return

        tokenStatus.value = LoginViewStatus.LoadingToken
        coroutineScope.launch {
            val response = gitHubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token
            loadUser()
        }
    }

    private fun loadUser() {
        tokenStatus.postValue(LoginViewStatus.LoadingToken)
        GlobalScope.launch {
            try {
                val user = gitHubUtils.getUser()
                tokenStatus.postValue(LoginViewStatus.LoadedToken(user))
                sharedPreferences.userLogin = user.login
            } catch(e: Exception){
                clearCookies()
            }
        }
    }

    fun startLogin(activity: Activity) {
        val authIntent = Intent(Intent.ACTION_VIEW, gitHubUtils.buildAuthGitHubUrl())
        activity.startActivity(authIntent)
    }

    fun clearCookies() {
        sharedPreferences.token = ""
        CookieManager.getInstance().setCookie("https://github.com", " ")

        CookieManager.getInstance().flush()
        tokenStatus.postValue(LoginViewStatus.EmptyToken)
    }

    fun destroy() {
        coroutineScope.coroutineContext.cancel()
    }
}

sealed class LoginViewStatus {
    object EmptyToken : LoginViewStatus()
    object LoadingToken : LoginViewStatus()
    data class LoadedToken(val user: UserInfoModel) : LoginViewStatus()
}