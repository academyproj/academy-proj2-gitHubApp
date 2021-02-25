package com.example.academy_proj2_githubapp.user_profile.data.models

import java.io.Serializable

sealed class UserToLoad : Serializable {
    object CurrentUser : UserToLoad()
    data class CustomUser(val login: String) : UserToLoad()
}
