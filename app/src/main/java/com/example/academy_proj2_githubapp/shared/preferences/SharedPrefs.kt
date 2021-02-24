package com.example.academy_proj2_githubapp.shared.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val context: Context
) {
    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_USERNAME = "KEY_USERNAME"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("preferences", MODE_PRIVATE)
    }

    var token: String by ShardePrefDelegate(sharedPreferences, KEY_TOKEN, "")
    var userLogin: String by ShardePrefDelegate(sharedPreferences, KEY_USERNAME, "")
}