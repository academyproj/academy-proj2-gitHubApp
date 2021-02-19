package com.example.academy_proj2_githubapp.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.UserSearchItemBinding
import com.example.academy_proj2_githubapp.search.data.models.UserFromSearchModel

class SearchAdapter(private val callback: (String) -> Unit) :
    ListAdapter<UserFromSearchModel, UserItemViewHolder>(UsersSearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)

        return UserItemViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserItemViewHolder(itemView: View, private val callback: (String) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = UserSearchItemBinding.bind(itemView)

    fun bind(user: UserFromSearchModel) {
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

class UsersSearchDiffCallback : DiffUtil.ItemCallback<UserFromSearchModel>() {
    override fun areItemsTheSame(
        oldItem: UserFromSearchModel,
        newItem: UserFromSearchModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserFromSearchModel,
        newItem: UserFromSearchModel
    ): Boolean {
        return oldItem == newItem
    }

}