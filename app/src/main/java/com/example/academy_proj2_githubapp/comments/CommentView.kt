package com.example.academy_proj2_githubapp.comments

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.MigrationCommentItemBinding
import com.example.academy_proj2_githubapp.reactions.models.ReactionType

class CommentView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    private val binding = MigrationCommentItemBinding.bind(
        inflate(
            context,
            R.layout.migration_comment_item,
            this
        )
    )

    fun setUserName(username: String) {
        binding.tvCommentUserNameMigrate.text = username
    }

    fun setBodyText(text: String) {
        binding.tvCommentBodyMigrate.text = text
    }

    fun setReactionButtonOnClick(action: (View) -> Unit) {
        binding.ibAddCommentReactionMigrate.setOnClickListener(action)
    }

    fun setAvatar(url: String) {
        Glide.with(binding.ivCommentAvatarMigrate)
            .load(url)
            .circleCrop()
            .into(binding.ivCommentAvatarMigrate)
    }

    fun updateReactions(reactions: HashMap<ReactionType, Int>) {
        reactions.keys.forEach { reaction ->
            reactions[reaction]?.let { quantity ->
                if (quantity > 0) {
                    val view = TextView(context).apply {
                        text = quantity.toString()
                        setTextColor(context.getColor(R.color.white))
                        textAlignment = TEXT_ALIGNMENT_CENTER
                        setTextSize(
                            TypedValue.COMPLEX_UNIT_PX,
                            resources.getDimension(R.dimen.rv_item_additional_size)
                        )
                        setCompoundDrawablesRelativeWithIntrinsicBounds(reaction.src, 0, 0, 0)
                    }
                    binding.llCommentReactionsMigrage.addView(view)
                }
            }
        }
    }


}