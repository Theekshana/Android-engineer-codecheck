/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.common.ErrorMessageDialogFragment
import jp.co.yumemi.android.code_check.common.hideKeyboard
import jp.co.yumemi.android.code_check.common.initRecyclerView
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.util.NetworkUtils.Companion.isNetworkAvailable
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
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
            viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
            githubVM = viewModel
            lifecycleOwner = this@HomeFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeGitHubRecyclerView()

        lifecycleScope.launch {
            binding.apply {
                viewModel.githubAccounts.observe(viewLifecycleOwner) {
                    when (it.status) {
                        DataStatus.Status.LOADING -> {
                            //Loading state
                        }

                        DataStatus.Status.SUCCESS -> {
                            it.data?.let { fetchedGitHubData ->
                                displayFetchedGitHubData(fetchedGitHubData)
                                Log.d("HomeFragment", "Data: $fetchedGitHubData")

                            }
                        }

                        DataStatus.Status.ERROR -> {
                            //Error state
                        }
                    }
                }
            }
        }

    }

    private fun displayFetchedGitHubData(gitHubAccountList: List<GitHubAccount>) {
        gitHubRepositoryAdapter.submitList(gitHubAccountList)
    }

    private fun initializeGitHubRecyclerView() {
        gitHubRepositoryAdapter =
            GitHubRepositoryAdapter(
                object : GitHubRepositoryAdapter.OnItemClickListener {
                    override fun itemClick(item: GitHubAccount) {
                        TODO("Not yet implemented")
                    }
                },
            )

        configureRecyclerViewLayout()
        configureSearchInputListener()

    }

    private fun configureRecyclerViewLayout() {
        binding.searchResultsRecyclerView.initRecyclerView(
            LinearLayoutManager(requireContext()),
            gitHubRepositoryAdapter
        )
    }

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



    private fun showErrorMessageDialog(message: String) {
        val dialogFragment = ErrorMessageDialogFragment.newInstance(message)
        dialogFragment.show(childFragmentManager, getString(R.string.errorMessageDialog))
    }


}
