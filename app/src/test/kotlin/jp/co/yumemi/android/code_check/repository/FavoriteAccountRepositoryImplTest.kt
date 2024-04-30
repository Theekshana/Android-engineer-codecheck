package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import jp.co.yumemi.android.code_check.exceptions.DatabaseOperationException
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
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
        verify(gitHubRepositoryDao).insertFavorite(mockObject)
    }

    /**
     * Test case for verifying the behavior of [FavoriteAccountRepository.insertFavoriteAccount]
     * when an exception is thrown during the insertion process.
     */
    @Test(expected = DatabaseOperationException::class)
    fun `test insertFavoriteAccount with exception`() = runBlocking {

        // Stub the DAO to throw an exception
        `when`(gitHubRepositoryDao.insertFavorite(mockObject))
            .thenThrow(RuntimeException("Failed to insert"))

        // When
        favoriteAccountRepository.insertFavoriteAccount(mockObject)

        // Verify that insertFavorite method was called on the DAO
        verify(gitHubRepositoryDao).insertFavorite(mockObject)
    }

}