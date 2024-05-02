/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.repositorydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.ErrorMessageDialogFragment
import jp.co.yumemi.android.code_check.databinding.FragmentRepoDetailsBinding
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.views.favourite.FavoritesViewModel

/**
 * Fragment representing the details screen for a specific GitHub repository. This fragment
 * retrieves repository details passed through arguments and displays them using data binding.
 */
@AndroidEntryPoint
class GitHubRepoDetailsFragment : Fragment() {

    // Data binding for the fragment layout
    private lateinit var binding: FragmentRepoDetailsBinding

    // Arguments passed to the fragment containing repository details
    private val args: GitHubRepoDetailsFragmentArgs by navArgs()

    // ViewModel instance responsible for managing repository details
    private lateinit var viewModel: GitHubRepoDetailsViewModel
    private lateinit var favoritesViewModel: FavoritesViewModel

    // The selected GitHub repository object obtained from arguments
    private lateinit var selectedRepository: GitHubAccount

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            // Initialize ViewModel using ViewModelProvider
            viewModel =
                ViewModelProvider(this@GitHubRepoDetailsFragment)[GitHubRepoDetailsViewModel::class.java]
            favoritesViewModel =
                ViewModelProvider(this@GitHubRepoDetailsFragment)[FavoritesViewModel::class.java]

            // Extract repository details from arguments
            selectedRepository = args.repository

            // Assign ViewModel to data binding variable
            repoDetails = viewModel
            lifecycleOwner = this@GitHubRepoDetailsFragment
        }

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize repository details
        setupRepositoryDetails()

        // Set up save button functionality
        setupSaveButton()

    }

    /**
     * Sets up the click listener for the save button.
     * When the button is clicked, it saves the selected repository as a favorite using the
     * [favoritesViewModel] and displays a success message.
     */
    private fun setupSaveButton() {
        binding.btnFabSaveRepository.setOnClickListener {
            favoritesViewModel.saveFavoriteAccount(selectedRepository)
            // Show success message after saving
            showSuccessMessage(getString(R.string.savedSuccess))
        }
    }

    /**
     * Shows a success or error message dialog.
     *
     * @param message The message to display in the dialog.
     */
    private fun showSuccessMessage(message: String) {
        // Create and show a dialog fragment based on the provided message and error flag
        val dialogFragment = ErrorMessageDialogFragment.newInstance(message, false)
        dialogFragment.show(childFragmentManager, getString(R.string.successMessageDialog))
    }

    /**
     * Loads the selected repository details into the ViewModel using the
     * `loadRepositoryDetails` method.
     */
    private fun setupRepositoryDetails() {
        viewModel.loadRepositoryDetails(selectedRepository)
    }

}
