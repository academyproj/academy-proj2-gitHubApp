package com.example.academy_proj2_githubapp.repository.ui.issues

import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.MATCH_PARENT
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.WRAP_CONTENT
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_proj2_githubapp.comments.CommentView
import com.example.academy_proj2_githubapp.comments.models.CommentModel
import com.example.academy_proj2_githubapp.comments.models.toMap

enum class CommentType {
    ISSUE,
    REGULAR
}

class IssueCommentsAdapter(
    private val openDialog: (Int) -> Unit,
) : ListAdapter<CommentModel, RecyclerView.ViewHolder>(CommentsDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) CommentType.ISSUE.ordinal
        else CommentType.REGULAR.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CommentView(parent.context)
        return if (viewType == CommentType.REGULAR.ordinal) CommentViewHolder(view, openDialog)
        else IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> {
                holder.bind(getItem(position))
            }
            is IssueViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

}

class IssueViewHolder(itemView: CommentView) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: CommentModel) {
        (itemView as CommentView).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            setUserName(comment.user.login)
            setBodyText(comment.body)

            updateReactions(comment.reactions.toMap())
            setAvatar(comment.user.avatarUrl)
        }
    }
}

class CommentViewHolder(
    itemView: CommentView,
    val openDialog: (Int) -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: CommentModel) {
        (itemView as CommentView).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            setUserName(comment.user.login)
            setBodyText(comment.body)
            setAvatar(comment.user.avatarUrl)
            setReactionButtonOnClick { openDialog(comment.id) }
            updateReactions(comment.reactions.toMap())
        }
    }
}

class CommentsDiffCallback : DiffUtil.ItemCallback<CommentModel>() {
    override fun areItemsTheSame(
        oldItem: CommentModel,
        newItem: CommentModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CommentModel,
        newItem: CommentModel
    ): Boolean {
        return oldItem == newItem
    }

}
