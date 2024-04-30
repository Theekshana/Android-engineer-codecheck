package jp.co.yumemi.android.code_check.views.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for [HomeViewModel].
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    // Create a test coroutine dispatcher
    private val testDispatcher = UnconfinedTestDispatcher()

    // Rule to make LiveData work with JUnit
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // The ViewModel under test
    private lateinit var viewModel: HomeViewModel

    // Mock objects
    @Mock
    private lateinit var mockGithubRepository: GithubRepository

    @Mock
    private lateinit var gitHubListObserver: Observer<DataStatus<List<GitHubAccount>>>

    @Before
    fun setUp() {
        // Initialize mocks and ViewModel
        openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockGithubRepository)
        viewModel.githubAccounts.observeForever(gitHubListObserver)
    }

    /**
     * Test case for verifying that [HomeViewModel.fetchGithubAccounts] updates LiveData
     * with fetched GitHub accounts.
     */
    @Test
    fun `test fetchGithubAccounts updates LiveData with fetched GitHub accounts`() =
        runBlocking {
            // Given
            val searchQuery = "kotlin"
            val mockData = listOf(
                mockObject
            )
            // Create a flow with mock data
            val flow = flowOf(DataStatus.success(mockData))
            // Stub the repository method to return the flow
            Mockito.`when`(mockGithubRepository.getGithubAccountsFromDataSource(searchQuery))
                .thenReturn(
                    flow
                )

            // Call the method under test
            viewModel.fetchGithubAccounts(searchQuery)

            // Verify that LiveData was updated with the expected data
            val observedValue = viewModel.githubAccounts.value
            assertEquals(DataStatus.success(mockData), observedValue)

        }
}