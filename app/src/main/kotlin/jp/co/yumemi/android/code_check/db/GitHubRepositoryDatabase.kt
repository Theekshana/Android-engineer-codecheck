package jp.co.yumemi.android.code_check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.co.yumemi.android.code_check.model.GitHubAccount

/**
 * Abstract class representing the Room database for GitHub repositories.
 * Extend this class to create a Room database instance.
 */

@Database(entities = [GitHubAccount::class], version = 1, exportSchema = false)
@TypeConverters(OwnerConverter::class)
abstract class GitHubRepositoryDatabase : RoomDatabase() {

    /**
     * Get an instance of the Data Access Object (DAO) for GitHub repositories.
     * Use this DAO to perform database operations related to GitHub repositories.
     * @return An instance of GitHubRepositoryDao.
     */
    abstract fun gitHubRepositoryDao(): GitHubRepositoryDao

}