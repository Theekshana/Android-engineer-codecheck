package jp.co.yumemi.android.code_check.views.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.initRecyclerView
import jp.co.yumemi.android.code_check.databinding.FragmentFavouritesBinding
import jp.co.yumemi.android.code_check.model.GitHubAccount

/**
 * Fragment for displaying a list of favorite GitHub repositories.
 */
@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    // Data binding for the fragment layout
    private lateinit var binding: FragmentFavouritesBinding

    // ViewModel for managing data and business logic related to the FavouritesFragment
    private lateinit var viewModel: FavoritesViewModel

    // Adapter for displaying a list of favorite repositories in the RecyclerView
    private lateinit var favoriteRepositoriesAdapter: FavoriteRepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(
            inflater,
            container,
            false
        )
        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter and observe changes in favorite repositories
        initializeFavoriteRepositoriesAdapter()
        observeFavoriteRepositories()

        // Set up swipe-to-delete functionality for repositories
        setupItemTouchHelper()

    }

    /**
     * Sets up swipe-to-delete functionality for the RecyclerView.
     */
    private fun setupItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val account = favoriteRepositoriesAdapter.differ.currentList[position]

                // Delete the swiped GitHub account from the ViewModel
                viewModel.deleteFavoriteAccount(account)

                // Display the undo option for the deleted repository
                displayUndoOptionForDeletedRepository(account)

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedRepoList)
        }
    }

    /**
     * Displays a Snack bar with an option to undo the deletion of a repository.
     *
     * @param account The deleted GitHub repository account.
     */
    private fun displayUndoOptionForDeletedRepository(account: GitHubAccount) {
        view?.let { view ->
            Snackbar.make(
                view,
                getString(R.string.successfullyDeleted),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(getString(R.string.undo)) {
                    // Call the undoDeleteAction function to restore the deleted account
                    undoDeleteAction(account)
                }
                show()
            }
        }
    }

    /**
     * Restores a previously deleted GitHub repository account by saving it back to the database.
     *
     * @param account The GitHub repository account to be restored.
     */
    private fun undoDeleteAction(account: GitHubAccount) {
        viewModel.saveFavoriteAccount(account)
    }

    /**
     * Observes changes in the list of favorite repositories.
     */
    private fun observeFavoriteRepositories() {
        viewModel.favoriteRepositories.observe(viewLifecycleOwner) { favoriteRepos ->
            favoriteRepositoriesAdapter.differ.submitList(favoriteRepos)
        }
    }

    /**
     * Initializes the adapter for the RecyclerView.
     */
    private fun initializeFavoriteRepositoriesAdapter() {
        favoriteRepositoriesAdapter =
            FavoriteRepositoriesAdapter(object : FavoriteRepositoriesAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubAccount) {
                    TODO("Not yet implemented")
                }
            })
        setupFavoriteRepositoriesRecyclerView()
    }

    /**
     * Sets up the RecyclerView with the initialized adapter.
     */
    private fun setupFavoriteRepositoriesRecyclerView() {
        binding.rvSavedRepoList.initRecyclerView(
            LinearLayoutManager(requireContext()),
            favoriteRepositoriesAdapter
        )
    }
}