package jp.co.yumemi.android.code_check.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.co.yumemi.android.code_check.constants.Constants.GITHUB_REPOSITORY_TABLE
import jp.co.yumemi.android.code_check.model.GitHubAccount
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing GitHub repository entities.
 */
@Dao
interface GitHubRepositoryDao {

    /**
     * Inserts a favorite GitHub repository account into the database.
     *
     * @param account The GitHub repository account to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(account: GitHubAccount)

    /**
     * Retrieves all favorite GitHub repository accounts from the database.
     *
     * @return A flow emitting the list of favorite GitHub repository accounts.
     */
    @Query("SELECT * FROM $GITHUB_REPOSITORY_TABLE")
    fun getAllFavorites(): Flow<List<GitHubAccount>>

    /**
     * Deletes a favorite GitHub repository account from the database.
     *
     * @param account The GitHub repository account to delete.
     */
    @Delete
    suspend fun deleteFavorite(account: GitHubAccount)

}