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
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad

class SearchAdapter(private val callback: (UserToLoad) -> Unit) :
    ListAdapter<UserInfoModel, UserItemViewHolder>(UsersSearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)

        return UserItemViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserItemViewHolder(itemView: View, private val callback: (UserToLoad) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = UserSearchItemBinding.bind(itemView)

    fun bind(user: UserInfoModel) {
        binding.tvUserItemName.text = user.login
        Glide.with(itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.ivUserItemAvatar)

        binding.root.setOnClickListener {
            callback(
                UserToLoad.CustomUser(user.login)
            )
        }
    }
}

class UsersSearchDiffCallback : DiffUtil.ItemCallback<UserInfoModel>() {
    override fun areItemsTheSame(
        oldItem: UserInfoModel,
        newItem: UserInfoModel
    ): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(
        oldItem: UserInfoModel,
        newItem: UserInfoModel
    ): Boolean {
        return oldItem == newItem
    }

}