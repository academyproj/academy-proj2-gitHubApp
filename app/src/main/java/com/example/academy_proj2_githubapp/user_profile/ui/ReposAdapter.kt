package com.example.academy_proj2_githubapp.user_profile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.RepoItemBinding
import com.example.academy_proj2_githubapp.user_profile.data.models.UserRepoModel

class ReposAdapter :
    ListAdapter<UserRepoModel, RepoItemViewHolder>(UsersSearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)

        return RepoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = RepoItemBinding.bind(itemView)

    fun bind(repo: UserRepoModel) {
        binding.apply {
            tvRepoItemName.text = repo.name
            tvRepoItemWatchers.text = repo.watchersCount.toString()
            tvRepoItemCreated.text = repo.createdAt
        }
    }
}

class UsersSearchDiffCallback : DiffUtil.ItemCallback<UserRepoModel>() {
    override fun areItemsTheSame(
        oldItem: UserRepoModel,
        newItem: UserRepoModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserRepoModel,
        newItem: UserRepoModel
    ): Boolean {
        return oldItem == newItem
    }

}