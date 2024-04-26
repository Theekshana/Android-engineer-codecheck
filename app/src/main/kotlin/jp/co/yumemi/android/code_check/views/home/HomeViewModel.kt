/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the HomeFragment, responsible for managing data related to GitHub accounts.
 * Uses [githubRepository] to fetch GitHub accounts from a data source.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    // LiveData for holding GitHub account data along with its loading or error status
    private val _githubAccounts = MutableLiveData<DataStatus<List<GitHubAccount>>>()
    val githubAccounts: LiveData<DataStatus<List<GitHubAccount>>> get() = _githubAccounts

    // Variable to store the current search query
    var currentSearchQuery: String? = null

    /**
     * Fetches GitHub accounts based on the specified search query.
     *
     * @param searchQuery The search query used to filter GitHub accounts.
     */
    fun fetchGithubAccounts(searchQuery: String) =
        viewModelScope.launch {
            githubRepository.getGithubAccountsFromDataSource(searchQuery)
                .collect { _githubAccounts.value = it }
        }
}

