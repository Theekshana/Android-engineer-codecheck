/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.ErrorMessageDialogFragment
import jp.co.yumemi.android.code_check.common.hideKeyboard
import jp.co.yumemi.android.code_check.common.initRecyclerView
import jp.co.yumemi.android.code_check.common.isVisible
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.util.NetworkUtils.Companion.isNetworkAvailable
import kotlinx.coroutines.launch

/**
 * This fragment represents the Home screen of the app. It displays a search bar for users to
 * enter their desired search query and presents a list of fetched GitHub repositories.
 */
class HomeFragment : Fragment() {

    // Data binding for the fragment layout
    private lateinit var binding: FragmentHomeBinding

    // ViewModel for the HomeFragment
    private lateinit var viewModel: HomeViewModel

    // Adapter for the GitHub repository RecyclerView
    private lateinit var gitHubRepositoryAdapter: GitHubRepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            // Initialize ViewModel using ViewModelProvider
            viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
            githubVM = viewModel
            lifecycleOwner = this@HomeFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView for displaying GitHub repositories
        initializeGitHubRepositoryAdapter()

        // Observe GitHub accounts LiveData and update UI accordingly
        lifecycleScope.launch {
            binding.apply {
                viewModel.githubAccounts.observe(viewLifecycleOwner) {
                    when (it.status) {
                        DataStatus.Status.LOADING -> {
                            binding.lottieProgressBar.isVisible(true)
                        }

                        DataStatus.Status.SUCCESS -> {
                            binding.lottieProgressBar.isVisible(false)
                            it.data?.let { fetchedGitHubData ->
                                displayFetchedGitHubData(fetchedGitHubData)
                            }
                        }

                        DataStatus.Status.ERROR -> {
                            binding.lottieProgressBar.isVisible(true)
                        }
                    }
                }
            }
        }

    }

    /**
     * Updates the RecyclerView adapter with the fetched list of GitHub repositories.
     * @param gitHubAccountList List of GitHubAccount objects containing repository data.
     */
    private fun displayFetchedGitHubData(gitHubAccountList: List<GitHubAccount>) {
        gitHubRepositoryAdapter.submitList(gitHubAccountList)
    }

    /**
     * Initializes the GitHubRepositoryAdapter for displaying GitHub repositories in a RecyclerView.
     *
     * This method creates a new instance of the GitHubRepositoryAdapter and configures the item click listener.
     * Additionally, it prepares the adapter to be set on the RecyclerView.
     */
    private fun initializeGitHubRepositoryAdapter() {
        // Create a new instance of GitHubRepositoryAdapter and set up item click listener
        gitHubRepositoryAdapter = GitHubRepositoryAdapter(
            object : GitHubRepositoryAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubAccount) {
                    // Navigate to the GitHub repository details screen when an item is clicked
                    navigateToGitHubRepoDetails(item)
                }
            },
        )

        // Configure layout for RecyclerView
        configureRecyclerViewLayout()

        // Configure search input listener for filtering repositories
        configureSearchInputListener()

    }

    /**
     * Navigates from the home screen to the GitHub repository details screen.
     *
     * @param item The GitHubAccount object representing the repository details to navigate to.
     */
    private fun navigateToGitHubRepoDetails(item: GitHubAccount) {
        findNavController().navigate(
            // Create an action from the home fragment to the repository details fragment
            HomeFragmentDirections.actionHomeFragmentToGitHubRepoDetailsFragment(item)
        )
    }

    /**
     * Sets up the layout manager (LinearLayoutManager) and adapter for the RecyclerView.
     */
    private fun configureRecyclerViewLayout() {
        binding.searchResultsRecyclerView.initRecyclerView(
            LinearLayoutManager(requireContext()),
            gitHubRepositoryAdapter
        )
    }

    /**
     * Configures the listener for the search input text field. When the search action is triggered
     * (IME_ACTION_SEARCH), it retrieves the user input from the text field, trims it, and performs
     * the search operation. If the input is empty or null, it displays an error message dialog.
     * After handling the search action, it ensures that the soft keyboard is hidden.
     */
    private fun configureSearchInputListener() {

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val userInput = viewModel.currentSearchQuery?.trim()

                    if (userInput.isNullOrEmpty()) {
                        showErrorMessageDialog(getString(R.string.invalidInput))
                        return@setOnEditorActionListener true
                    } else {
                        hideKeyboard(editText)
                        performSearch(userInput)
                        return@setOnEditorActionListener true
                    }
                }
                return@setOnEditorActionListener false
            }
    }

    /**
     * Performs a search operation based on the user input. If the network connection is available,
     * it clears the existing list of GitHub repositories, fetches new repositories based on the
     * provided search query, and updates the UI accordingly. If there is no network connection,
     * it displays an error message dialog indicating the absence of internet connectivity.
     *
     * @param userInput The user input to be searched.
     */
    private fun performSearch(userInput: String) {

        if (isNetworkAvailable()) {
            gitHubRepositoryAdapter.submitList(emptyList())
            if (userInput.isNotEmpty()) {
                viewModel.fetchGithubAccounts(userInput)
            }
        } else {
            showErrorMessageDialog(getString(R.string.noInternetConnection))
        }
    }

    /**
     * Shows an error message dialog with the specified message. It creates a new instance of
     * ErrorMessageDialogFragment with the provided error message and displays it using the child
     * FragmentManager.
     *
     * @param message The error message to be displayed.
     */
    private fun showErrorMessageDialog(message: String) {
        val dialogFragment = ErrorMessageDialogFragment.newInstance(message)
        dialogFragment.show(childFragmentManager, getString(R.string.errorMessageDialog))
    }

}
