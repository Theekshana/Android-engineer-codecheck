package jp.co.yumemi.android.code_check.views.repositorydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.model.GitHubAccount
import javax.inject.Inject

@HiltViewModel
class GitHubRepoDetailsViewModel @Inject constructor(

) : ViewModel() {

    private val _repositoryDetails = MutableLiveData<GitHubAccount>(null)
    val repositoryDetails: LiveData<GitHubAccount> get() = _repositoryDetails

    fun loadRepositoryDetails(repositoryDetails: GitHubAccount) {

        _repositoryDetails.value = repositoryDetails

    }
}