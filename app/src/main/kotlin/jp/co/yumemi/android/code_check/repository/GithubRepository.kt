package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import kotlinx.coroutines.flow.Flow

/**
 * Interface for retrieving GitHub accounts from a data source.
 */
interface GithubRepository {
    /**
     * Retrieves GitHub accounts based on the specified search query.
     *
     * @param searchQuery The search query used to filter GitHub accounts.
     * @return A Flow emitting DataStatus objects containing the list of GitHub accounts.
     */
    suspend fun getGithubAccountsFromDataSource(
        // The search query used to filter GitHub accounts.
        searchQuery: String
    ): Flow<DataStatus<List<GitHubAccount>>>

}