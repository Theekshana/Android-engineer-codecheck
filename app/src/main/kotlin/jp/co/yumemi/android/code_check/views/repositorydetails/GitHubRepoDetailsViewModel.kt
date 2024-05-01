package jp.co.yumemi.android.code_check.views.repositorydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubAccount
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the repository details data and exposing it
 * through LiveData.
 */
@HiltViewModel
class GitHubRepoDetailsViewModel @Inject constructor() : ViewModel() {

    // LiveData to hold the repository details
    private val _repositoryDetails = MutableLiveData<GitHubAccount>(null)
    val repositoryDetails: LiveData<GitHubAccount> get() = _repositoryDetails

    /**
     * Updates the [_repositoryDetails] LiveData with the provided GitHubAccount object.
     * This method is typically called from other parts of your application to set the
     * repository details for which information needs to be displayed.
     * @param repositoryDetails A GitHubAccount object containing details about the repository.
     */
    fun loadRepositoryDetails(repositoryDetails: GitHubAccount) {

        _repositoryDetails.value = repositoryDetails

    }
}