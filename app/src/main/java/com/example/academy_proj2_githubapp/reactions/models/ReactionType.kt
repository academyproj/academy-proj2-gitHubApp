package com.example.academy_proj2_githubapp.reactions.models

import androidx.annotation.IdRes
import com.example.academy_proj2_githubapp.R

enum class ReactionType(name: String) {
    PLUS_ONE("+1"),
    MINUS_ONE("-1"),
    EYES("eyes"),
    UNDEFINED("undefined")
}