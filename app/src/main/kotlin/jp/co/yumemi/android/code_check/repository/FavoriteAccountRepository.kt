package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.GitHubAccount
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing favorite GitHub accounts.
 */
interface FavoriteAccountRepository {

    /**
     * Insert a new favorite GitHub account.
     *
     * @param account The GitHub account to insert.
     */
    suspend fun insertFavoriteAccount(account: GitHubAccount)

    /**
     * Get a flow of all favorite GitHub accounts.
     *
     * @return A flow emitting a list of favorite GitHub accounts.
     */
    fun getFavoriteRepositories(): Flow<List<GitHubAccount>>

    /**
     * Delete a favorite GitHub account.
     *
     * @param account The GitHub account to delete.
     */
    suspend fun deleteFavoriteAccount(account: GitHubAccount)
}