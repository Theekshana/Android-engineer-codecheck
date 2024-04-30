package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.db.GitHubRepositoryDao
import jp.co.yumemi.android.code_check.exceptions.DatabaseOperationException
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    /**
     * Unit test for verifying the behavior of [FavoriteAccountRepository.getFavoriteRepositories].
     */
    @Test
    fun `test getFavoriteRepositories`() = runBlocking {
        // Mock favorite repositories
        val favoriteRepos = listOf(
            mockObject
        )
        // Create a flow of favorite repositories
        val flow = flowOf(favoriteRepos)

        // Stub the DAO to return the flow when getAllFavorites is called
        `when`(gitHubRepositoryDao.getAllFavorites()).thenReturn(flow)

        // Call the method under test
        val result = favoriteAccountRepository.getFavoriteRepositories().toList()

        // Assert that the result matches the expected list of favorite repositories
        assertEquals(listOf(favoriteRepos), result)
    }

    /**
     * Unit test for verifying the behavior of [FavoriteAccountRepository.getFavoriteRepositories]
     * when a database error occurs.
     */
    @Test
    fun `test getFavoriteRepositories with database error`() = runBlocking {

        // Stub the DAO to throw a RuntimeException when getAllFavorites is called
        `when`(gitHubRepositoryDao.getAllFavorites())
            .thenThrow(RuntimeException("Failed to fetch"))

        // Call the method under test
        val result = kotlin.runCatching {
            favoriteAccountRepository.getFavoriteRepositories().toList()
        }

        // Assert that the result is a failure
        assertTrue(result.isFailure)

        // Assert that the exception thrown is a RuntimeException
        assertTrue(result.exceptionOrNull() is RuntimeException)

        // Assert that the exception message matches the expected error message
        assertEquals(
            "Failed to fetch",
            result.exceptionOrNull()?.message
        )
    }

    /**
     * Unit test for verifying the behavior of [FavoriteAccountRepository.deleteFavoriteAccount]
     * when the deletion is successful.
     */
    @Test
    fun `test deleteFavoriteAccount success`() = runBlocking {

        // Stub the DAO to return Unit when deleteFavorite is called with mockObject
        `when`(gitHubRepositoryDao.deleteFavorite(mockObject)).thenReturn(Unit)

        // Call the method under test
        favoriteAccountRepository.deleteFavoriteAccount(mockObject)

        // Verify that deleteFavorite was called with the correct parameter
        verify(gitHubRepositoryDao).deleteFavorite(mockObject)
    }

}