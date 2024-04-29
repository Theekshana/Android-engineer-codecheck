package jp.co.yumemi.android.code_check.views.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutFavoriteListItemBinding
import jp.co.yumemi.android.code_check.model.GitHubAccount
import javax.inject.Inject

/**
 * Adapter for displaying a list of favorite GitHub repositories.
 *
 * @param onItemClickListener Listener for item click events.
 */
class FavoriteRepositoriesAdapter @Inject constructor(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteRepositoriesAdapter.RepositoryViewHolder>() {

    /**
     * ViewHolder for each repository item.
     *
     * @param viewBinding The binding object for the layout.
     */
    inner class RepositoryViewHolder(
        private val viewBinding: LayoutFavoriteListItemBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList.getOrNull(position)
                    item?.let { onItemClickListener.itemClick(it) }
                }
            }
        }

        /**
         * Binds data to the item view.
         *
         * @param item The GitHub repository item to bind.
         */
        fun bind(item: GitHubAccount) {
            viewBinding.apply {
                repository = item
                executePendingBindings()
            }
        }
    }

    /**
     * Listener interface for item click events.
     */
    interface OnItemClickListener {
        fun itemClick(item: GitHubAccount)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<GitHubAccount>() {
        override fun areItemsTheSame(
            oldItem: GitHubAccount,
            newItem: GitHubAccount
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GitHubAccount,
            newItem: GitHubAccount
        ): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * [AsyncListDiffer] to compute the difference between two lists.
     * It detects changes in the list contents and computes the minimal
     * number of updates needed to convert the old list into the new one.
     */
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutFavoriteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val account = differ.currentList[position]
        holder.bind(account)

    }

    override fun getItemCount() = differ.currentList.size

}





