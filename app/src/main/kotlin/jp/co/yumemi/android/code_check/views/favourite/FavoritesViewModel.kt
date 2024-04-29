package jp.co.yumemi.android.code_check.views.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.repository.FavoriteAccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing favorite GitHub accounts.
 *
 * This ViewModel is responsible for providing data related to favorite GitHub accounts
 * to the associated UI component, typically a Fragment or Activity.
 *
 * @param favoriteAccountRepository The repository responsible for handling favorite account data operations.
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteAccountRepository: FavoriteAccountRepository
) : ViewModel() {

    /**
     * Initializes the FavoritesViewModel by loading the list of favorite repositories.
     *
     * This initializer automatically triggers the loading of the list of favorite repositories
     * when the FavoritesViewModel is created. It calls the loadFavoriteRepositories method
     * to fetch the list of repositories from the repository and update the LiveData accordingly.
     * This ensures that the UI is immediately populated with the latest data upon ViewModel creation.
     */
    init {
        loadFavoriteRepositories()
    }

    // LiveData to hold the list of favorite repositories
    private val _favoriteRepositories = MutableLiveData<List<GitHubAccount>>()
    val favoriteRepositories: LiveData<List<GitHubAccount>> get() = _favoriteRepositories

    /**
     * Saves a GitHub account as a favorite.
     *
     * @param account The GitHub account to be saved.
     */
    fun saveFavoriteAccount(account: GitHubAccount) {
        viewModelScope.launch {
            favoriteAccountRepository.insertFavoriteAccount(account)
        }
    }

    /**
     * Loads the list of favorite repositories.
     *
     * This method fetches the list of favorite repositories from the repository
     * and updates the [_favoriteRepositories] LiveData with the fetched data.
     */
    private fun loadFavoriteRepositories() {
        viewModelScope.launch {
            favoriteAccountRepository.getFavoriteRepositories().collect { favoriteRepos ->

                _favoriteRepositories.value = favoriteRepos

            }
        }
    }

    /**
     * Deletes a favorite GitHub account.
     *
     * @param account The GitHub account to be deleted.
     */
    fun deleteFavoriteAccount(account: GitHubAccount) {
        viewModelScope.launch {
            favoriteAccountRepository.deleteFavoriteAccount(account)
        }
    }
}