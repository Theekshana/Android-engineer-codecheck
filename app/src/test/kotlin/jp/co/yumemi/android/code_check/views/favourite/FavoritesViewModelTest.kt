package jp.co.yumemi.android.code_check.views.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.repository.FavoriteAccountRepository
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for the [FavoritesViewModel] class.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    // Test coroutine dispatcher for running tests synchronously
    private val testDispatcher = UnconfinedTestDispatcher()

    // Rule to make LiveData work synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Mocked repository
    @Mock
    private lateinit var favoriteAccountRepository: FavoriteAccountRepository

    // ViewModel to be tested
    private lateinit var viewModel: FavoritesViewModel

    /**
     * Set up the test environment.
     */
    @Before
    fun setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this)

        // Set main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel with mocked repository
        viewModel = FavoritesViewModel(favoriteAccountRepository)
    }

    /**
     * Unit test to verify that calling [FavoriteAccountRepository.insertFavoriteAccount]
     * inserts the provided account into the [favoriteAccountRepository].
     */
    @Test
    fun `test saveFavoriteAccount inserts account into favoriteAccountRepository`() =

        runBlocking {

            // Given a mock GitHub account object and its insertion into the repository
            `when`(favoriteAccountRepository.insertFavoriteAccount(mockObject))
                .thenReturn(Unit)

            // When the saveFavoriteAccount method is called with the mock account
            viewModel.saveFavoriteAccount(mockObject)

            // Then verify that the account was inserted into the repository
            verify(favoriteAccountRepository).insertFavoriteAccount(mockObject)

        }
}