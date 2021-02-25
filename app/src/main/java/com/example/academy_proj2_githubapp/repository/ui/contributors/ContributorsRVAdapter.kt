package com.example.academy_proj2_githubapp.repository.ui.contributors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.UserSearchItemBinding
import com.example.academy_proj2_githubapp.repository.data.models.UserModel

class ContributorsRVAdapter(private val callback: (String) -> Unit) :
    ListAdapter<UserModel, ContributorsViewHolder>(ContributorsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsViewHolder {
        //TODO R.layout contributor search item or not
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)

        return ContributorsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ContributorsViewHolder(itemView: View, private val callback: (String) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = UserSearchItemBinding.bind(itemView)

    fun bind(user: UserModel) {
        binding.tvUserItemName.text = user.login
        Glide.with(itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.ivUserItemAvatar)

        binding.root.setOnClickListener {
            callback(user.login)
        }
    }
}

class ContributorsDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ): Boolean {
        return oldItem == newItem
    }
}