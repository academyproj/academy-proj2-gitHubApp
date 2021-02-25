package com.example.academy_proj2_githubapp.login.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academy_proj2_githubapp.login.data.GitHubUtils
import com.example.academy_proj2_githubapp.login.data.LoginService
import com.example.academy_proj2_githubapp.login.data.UserService
import com.example.academy_proj2_githubapp.login.data.models.AccessToken
import com.example.academy_proj2_githubapp.repository.data.models.UserModel
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchErrors
import com.example.academy_proj2_githubapp.search.data.models.UsersSearchResponseData
import com.example.academy_proj2_githubapp.search.ui.SearchViewModel
import com.example.academy_proj2_githubapp.search.ui.SearchViewState
import com.example.academy_proj2_githubapp.shared.async.Multithreading
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

            Log.d("TAG", "token ${sharedPreferences.token}")
            loadUser()
        }
    }

    private fun loadUser() {
        tokenStatus.postValue(LoginViewStatus.LoadingToken)
        GlobalScope.launch {
            tokenStatus.postValue(LoginViewStatus.LoadedToken(gitHubUtils.getUser()))
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
}

sealed class LoginViewStatus {
    object EmptyToken : LoginViewStatus()
    object LoadingToken : LoginViewStatus()
    data class LoadedToken(val user: UserModel) : LoginViewStatus()
}