package com.example.academy_proj2_githubapp.reactions.models

import androidx.annotation.IdRes
import com.example.academy_proj2_githubapp.R

enum class ReactionType(val content: String, @IdRes val src: Int) {
    PLUS_ONE("+1", R.drawable.plus_one),
    MINUS_ONE("-1", R.drawable.minus_one),
    EYES("eyes", R.drawable.eyes),
    ROCKET("rocket", R.drawable.rocket),
    HOORAY("hooray", R.drawable.hooray),
    HEART("heart", R.drawable.heart),
    CONFUSED("confused", R.drawable.confused),
    LAUGH("laugh", R.drawable.laugh),
    UNDEFINED("undefined", R.drawable.ic_baseline_add_reaction_24)
}