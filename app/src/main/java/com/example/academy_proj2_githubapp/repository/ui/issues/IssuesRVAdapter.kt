package com.example.academy_proj2_githubapp.repository.ui.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_proj2_githubapp.R
import com.example.academy_proj2_githubapp.databinding.IssueItemBinding
import com.example.academy_proj2_githubapp.repository.data.models.IssueModel

class IssuesRVAdapter(private val callback: (IssueCallbackModel) -> Unit) :
    ListAdapter<IssueModel, IssuesViewHolder>(IssuesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.issue_item, parent, false)

        return IssuesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class IssuesViewHolder(itemView: View, private val callback: (IssueCallbackModel) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = IssueItemBinding.bind(itemView)

    fun bind(issue: IssueModel) {
        binding.tvIssuesItemName.text = issue.user.login
        binding.tvIssuesItemTitle.text = issue.title

        binding.root.setOnClickListener {
            callback(
                IssueCallbackModel(
                    owner = issue.user.login,
                    repo = issue.repoUrl.substringAfterLast("/"),
                    issue = issue.number
                )
            )
        }
    }
}

class IssuesDiffCallback : DiffUtil.ItemCallback<IssueModel>() {
    override fun areItemsTheSame(
        oldItem: IssueModel,
        newItem: IssueModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: IssueModel,
        newItem: IssueModel
    ): Boolean {
        return oldItem == newItem
    }
}

data class IssueCallbackModel(
    val owner: String,
    val repo: String,
    val issue: Int,
)