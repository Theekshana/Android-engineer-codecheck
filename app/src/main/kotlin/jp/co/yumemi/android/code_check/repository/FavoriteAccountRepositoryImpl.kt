package jp.co.yumemi.android.code_check.repository

import android.util.Log
import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import jp.co.yumemi.android.code_check.exceptions.DatabaseOperationException
import jp.co.yumemi.android.code_check.model.GitHubAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

/**
 * Implementation of the [FavoriteAccountRepository] interface.
 *
 * @param gitHubRepositoryDao The DAO for GitHub repositories.
 */
class FavoriteAccountRepositoryImpl @Inject constructor(
    private val gitHubRepositoryDao: GitHubRepositoryDao
) : FavoriteAccountRepository {

    /**
     * Inserts a favorite GitHub account into the database.
     *
     * @param account The GitHub account to insert.
     * @throws DatabaseOperationException if an error occurs while inserting the account.
     */
    override suspend fun insertFavoriteAccount(account: GitHubAccount) {
        try {
            gitHubRepositoryDao.insertFavorite(account)
        } catch (e: Exception) {

            throw DatabaseOperationException("Failed to insert favorite account: ${e.message}")
        }
    }

    /**
     * Retrieves all favorite GitHub repositories from the database.
     *
     * @return A flow emitting a list of favorite GitHub repositories.
     */
    override fun getFavoriteRepositories(): Flow<List<GitHubAccount>> {
        return gitHubRepositoryDao.getAllFavorites()
            .catch { e ->
                Log.e("codeCheck", "Failed to fetch favorite repositories: ${e.message}")
                emit(emptyList())
            }
    }

    /**
     * Deletes a favorite GitHub account from the database.
     *
     * @param account The GitHub account to delete.
     * @throws DatabaseOperationException if an error occurs while deleting the account.
     */
    override suspend fun deleteFavoriteAccount(account: GitHubAccount) {
        try {
            return gitHubRepositoryDao.deleteFavorite(account)
        } catch (e: Exception) {
            throw DatabaseOperationException("Failed to delete favorite account: ${e.message}")

        }
    }
}