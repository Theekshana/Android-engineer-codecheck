package jp.co.yumemi.android.code_check.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.constants.Constants.GITHUB_REPOSITORY_DB_NAME
import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import jp.co.yumemi.android.code_check.db.GitHubRepositoryDatabase
import jp.co.yumemi.android.code_check.repository.FavoriteAccountRepository
import jp.co.yumemi.android.code_check.repository.FavoriteAccountRepositoryImpl
import javax.inject.Singleton

/**
 * Module responsible for providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides an instance of the GitHub repository database.
     *
     * @param application The application context.
     * @return An instance of GitHubRepositoryDatabase.
     */
    @Singleton
    @Provides
    fun provideGitHubRepositoryDatabase(application: Application): GitHubRepositoryDatabase {
        return Room.databaseBuilder(
            application,
            GitHubRepositoryDatabase::class.java,
            GITHUB_REPOSITORY_DB_NAME
        ).build()
    }

    /**
     * Provides the Data Access Object (DAO) for GitHub repositories.
     *
     * @param database The GitHub repository database.
     * @return An instance of GitHubRepositoryDao.
     */
    @Provides
    @Singleton
    fun provideGitHubRepositoryDao(database: GitHubRepositoryDatabase): GitHubRepositoryDao {
        return database.gitHubRepositoryDao()
    }

    /**
     * Provides an instance of the implementation of the FavoriteAccountRepository interface.
     *
     * @param gitHubRepositoryDao The DAO for GitHub repositories.
     * @return An instance of the FavoriteAccountRepository implementation.
     */
    @Singleton
    @Provides
    fun provideFavoriteAccountRepository(
        gitHubRepositoryDao: GitHubRepositoryDao
    ): FavoriteAccountRepository {
        return FavoriteAccountRepositoryImpl(gitHubRepositoryDao)
    }

}