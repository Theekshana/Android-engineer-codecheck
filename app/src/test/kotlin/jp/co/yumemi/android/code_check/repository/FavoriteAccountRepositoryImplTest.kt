package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteAccountRepositoryImplTest {

    @Mock
    private lateinit var gitHubRepositoryDao: GitHubRepositoryDao

    private lateinit var favoriteAccountRepository: FavoriteAccountRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        favoriteAccountRepository = FavoriteAccountRepositoryImpl(gitHubRepositoryDao)
    }

    /**
     * Test case for verifying the behavior of [FavoriteAccountRepository.insertFavoriteAccount] function.
     */
    @Test
    fun `test insertFavoriteAccount`() = runBlocking {

        // Call the insertFavoriteAccount function with a mock GitHub account
        favoriteAccountRepository.insertFavoriteAccount(mockObject)

        // Verify that the insertFavorite function of the gitHubRepositoryDao is called with the same mock object
        Mockito.verify(gitHubRepositoryDao).insertFavorite(mockObject)
    }

}