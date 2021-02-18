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
import com.example.academy_proj2_githubapp.search.data.UserFromSearchData

class SearchAdapter :
    ListAdapter<UserFromSearchData, UserItemViewHolder>(UsersSearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)

        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = UserSearchItemBinding.bind(itemView)

    fun bind(user: UserFromSearchData) {
        binding.tvUserItemName.text = user.login
        Glide.with(itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.ivUserItemAvatar)
    }
}

class UsersSearchDiffCallback : DiffUtil.ItemCallback<UserFromSearchData>() {
    override fun areItemsTheSame(
        oldItem: UserFromSearchData,
        newItem: UserFromSearchData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserFromSearchData,
        newItem: UserFromSearchData
    ): Boolean {
        return oldItem == newItem
    }

}