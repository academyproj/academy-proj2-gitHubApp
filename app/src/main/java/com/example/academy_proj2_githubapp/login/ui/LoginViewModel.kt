package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Cookie
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
        Log.d("TAG" , "check ${sharedPreferences.token}")
        if (sharedPreferences.token == "") {
            tokenStatus.postValue(TokenStatus.EMPTY)
            getToken(activity)
        } else {
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
            val user = gitHubUtils.getUser()

            Log.d("TAG", "token $token")
            tokenStatus.postValue(TokenStatus.LOADED)
        }
    }

    fun startLogin(activity: Activity) {
        val authIntent = Intent(Intent.ACTION_VIEW, gitHubUtils.buildAuthGitHubUrl())
        activity.startActivity(authIntent)
    }


    //TODO fix or delete
    fun clearCookies() {
        sharedPreferences.token = ""
        CookieManager.getInstance().setCookie("https://github.com", " ")

        CookieManager.getInstance().flush()

        Log.d("TAG" , CookieManager.getInstance().hasCookies().toString())
        tokenStatus.postValue(TokenStatus.EMPTY)
    }
}