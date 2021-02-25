package com.example.academy_proj2_githubapp.reactions.models

import androidx.annotation.IdRes
import com.example.academy_proj2_githubapp.R

enum class ReactionType(val content: String, @IdRes val src: Int) {
    PLUS_ONE("+1", R.drawable.ic_plus_one),
    MINUS_ONE("-1", R.drawable.ic_minus_one),
    EYES("eyes", R.drawable.ic_eyes),
    ROCKET("rocket", R.drawable.ic_rocket),
    HOORAY("hooray", R.drawable.ic_hooray),
    HEART("heart", R.drawable.ic_heart),
    CONFUSED("confused", R.drawable.ic_confused),
    LAUGH("laugh", R.drawable.ic_laugh),
    UNDEFINED("undefined", R.drawable.ic_baseline_add_reaction_24)
}