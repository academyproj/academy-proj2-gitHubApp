package com.example.academy_proj2_githubapp.repository.data.mappers

import com.example.academy_proj2_githubapp.reactions.models.ReactionData
import com.example.academy_proj2_githubapp.reactions.models.ReactionModel
import com.example.academy_proj2_githubapp.reactions.models.ReactionType
import com.example.academy_proj2_githubapp.repository.data.models.IssueErrors
import com.example.academy_proj2_githubapp.shared.async.Result
import com.example.academy_proj2_githubapp.shared.preferences.SharedPrefs
import javax.inject.Inject

class ReactionsResultMapper @Inject constructor(
    private val sharedPrefs: SharedPrefs
) {
    fun map(result: Result<List<ReactionData>, IssueErrors>): Result<List<ReactionModel>, IssueErrors> {
        val userLogin = sharedPrefs.userLogin

        return result.mapSuccess {
            it.filter { r -> r.user.login == userLogin }
                .map { reaction ->
                    val type = when (reaction.content) {
                        ReactionType.MINUS_ONE.content -> ReactionType.MINUS_ONE
                        ReactionType.PLUS_ONE.content -> ReactionType.PLUS_ONE
                        ReactionType.ROCKET.content -> ReactionType.ROCKET
                        ReactionType.HOORAY.content -> ReactionType.HOORAY
                        ReactionType.LAUGH.content -> ReactionType.LAUGH
                        ReactionType.CONFUSED.content -> ReactionType.CONFUSED
                        ReactionType.HEART.content -> ReactionType.HEART
                        ReactionType.EYES.content -> ReactionType.EYES
                        else -> ReactionType.UNDEFINED
                    }
                    ReactionModel(
                        id = reaction.id,
                        createdAt = reaction.createdAt,
                        user = reaction.user,
                        type = type
                    )
                }
        }
    }

}
