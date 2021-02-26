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
import com.example.academy_proj2_githubapp.shared.models.UserInfoModel
import com.example.academy_proj2_githubapp.user_profile.data.models.UserToLoad

class ContributorsRVAdapter(private val callback: (UserToLoad) -> Unit) :
    ListAdapter<UserInfoModel, ContributorsViewHolder>(ContributorsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)

        return ContributorsViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ContributorsViewHolder(itemView: View, private val callback: (UserToLoad) -> Unit) :
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
                UserToLoad.CustomUser(
                    user.login
                )
            )
        }
    }
}

class ContributorsDiffCallback : DiffUtil.ItemCallback<UserInfoModel>() {
    override fun areItemsTheSame(
        oldItem: UserInfoModel,
        newItem: UserInfoModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UserInfoModel,
        newItem: UserInfoModel
    ): Boolean {
        return oldItem == newItem
    }
}