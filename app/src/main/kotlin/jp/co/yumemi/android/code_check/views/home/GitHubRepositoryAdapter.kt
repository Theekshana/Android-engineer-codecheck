package jp.co.yumemi.android.code_check.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.views.home.GitHubRepositoryAdapter.OnItemClickListener
import javax.inject.Inject

/**
 * Adapter for displaying a list of GitHub accounts in a RecyclerView.
 *
 * This adapter takes a list of [GitHubAccount] objects and an [OnItemClickListener] as arguments.
 * It inflates the layout for each item and binds the data to the view. When an item is clicked,
 * the adapter calls the `itemClick` method of the listener with the clicked item.
 */
class GitHubRepositoryAdapter @Inject constructor(
    /**
     * Listener for item clicks.
     */
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<GitHubAccount, GitHubRepositoryAdapter.GitHubAccountViewHolder>(diffUtil) {

    /**
     * ViewHolder class for each item in the list.
     *
     * This class holds the reference to the view binding for the layout and provides a `bind` method
     * to bind the data to the view.
     */
    inner class GitHubAccountViewHolder(
        private val viewBinding: LayoutRepoListItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            /**
             * SetOnClickListener for the root view of the ViewHolder.
             *
             * When the item is clicked, it checks the adapter position and calls the
             * `onItemClickListener.itemClick` method with the clicked item.
             */
            viewBinding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    onItemClickListener.itemClick(item)
                }
            }
        }

        /**
         * Binds the data of a [GitHubAccount] to the ViewHolder's view.
         *
         * This method uses the view binding to set the data to the appropriate views in the layout.
         */
        fun bind(item: GitHubAccount) {
            viewBinding.apply {
                githubAccount = item
                executePendingBindings()
            }
        }
    }

    /**
     * Interface for handling item clicks.
     *
     * This interface defines a single method `itemClick` that gets called when an item in the list is clicked.
     */
    interface OnItemClickListener {
        fun itemClick(item: GitHubAccount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubAccountViewHolder {
        return GitHubAccountViewHolder(
            LayoutRepoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GitHubAccountViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}

/**
 * DiffUtil callback for efficient item updates in the RecyclerView.
 *
 * This callback compares two [GitHubAccount] items and determines if they represent the same item
 * or if their content has changed.
 */
val diffUtil = object : DiffUtil.ItemCallback<GitHubAccount>() {
    override fun areItemsTheSame(oldItem: GitHubAccount, newItem: GitHubAccount): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubAccount, newItem: GitHubAccount): Boolean {
        return oldItem == newItem
    }
}