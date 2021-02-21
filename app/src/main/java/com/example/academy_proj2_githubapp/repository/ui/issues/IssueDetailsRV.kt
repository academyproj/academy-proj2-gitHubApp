package com.example.academy_proj2_githubapp.repository.ui.issues

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_proj2_githubapp.databinding.CommentHeaderBinding
import com.example.academy_proj2_githubapp.repository.data.models.IssueModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel

class IssueDetailsRV (private val callback: (IssueCallbackModel) -> Unit) :
    ListAdapter<IssueModel, RecyclerView.ViewHolder>(IssuesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class CommentHeaderVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding = CommentHeaderBinding.bind(itemView)

    fun bind() {

    }

}