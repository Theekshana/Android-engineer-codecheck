package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import javax.inject.Inject

class FavoriteAccountRepositoryImpl @Inject constructor(
    private val gitHubRepositoryDao: GitHubRepositoryDao
) : FavoriteAccountRepository {
}