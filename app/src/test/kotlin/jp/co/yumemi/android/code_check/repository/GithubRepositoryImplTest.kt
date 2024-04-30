package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.model.GitHubServerResponse
import jp.co.yumemi.android.code_check.model.Owner
import jp.co.yumemi.android.code_check.network.GithubApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


/**
 * Unit tests for [GithubRepositoryImpl].
 */
@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryImplTest {

    // Mocking the GithubApiService using Mockito. This will be used to simulate API responses.
    @Mock
    lateinit var mockGithubApiService: GithubApiService

    // The GithubRepositoryImpl instance to be tested. This will be initialized later in the test setup.
    private lateinit var githubRepository: GithubRepositoryImpl

    /**
     * Sets up the test environment.
     */
    @Before
    fun setUp() {
        // Initialize mock objects for testing using MockitoAnnotations
        MockitoAnnotations.openMocks(this)

        // Create an instance of the class under test, injecting the mocked GithubApiService.

        githubRepository = GithubRepositoryImpl(mockGithubApiService)
    }

    /**
     * Tests the behavior of [GithubRepositoryImpl.getGithubAccountsFromDataSource] when the API call is successful.
     */
    @Test
    fun `getGitHutAccountsFromDataSource should loading and then success when API call is successful`() =
        runBlocking {
            // Given
            val searchQuery = "kotlin"
            val mockResponse = Response.success(
                GitHubServerResponse(
                    items = listOf(
                        GitHubAccount(
                            name = "Repository Name",
                            owner = Owner(
                                "https://via.placeholder.com/150",
                                "https://dummyimage.com/150x150"
                            ),
                            repositoryLanguage = "Kotlin",
                            stargazersCount = 100,
                            watchersCount = 200,
                            forksCount = 50,
                            openIssuesCount = 10

                        )
                    )
                )
            )

            // Mock successful response from GithubApiService
            `when`(mockGithubApiService.fetchRepositoryInformation(searchQuery))
                .thenReturn(mockResponse)

            // Collect data from the Flow returned by the repository method under test
            val flow = githubRepository.getGithubAccountsFromDataSource(searchQuery)

            // Use a coroutine scope to collect data from the Flow
            flow.collect { dataStatus ->
                // Check loading state
                Assert.assertTrue(dataStatus is DataStatus)

                // Check success state
                when (dataStatus.status) {
                    DataStatus.Status.LOADING -> {
                        Assert.assertTrue(dataStatus.data == null)
                    }
                    // If the status is SUCCESS, assert that the returned data matches the expected list.
                    DataStatus.Status.SUCCESS -> {
                        val expectedList = listOf(
                            GitHubAccount(
                                name = "Repository Name",
                                owner = Owner(
                                    "https://via.placeholder.com/150",
                                    "https://dummyimage.com/150x150"
                                ),
                                repositoryLanguage = "Kotlin",
                                stargazersCount = 100,
                                watchersCount = 200,
                                forksCount = 50,
                                openIssuesCount = 10
                            )
                        )
                        assertEquals(expectedList, dataStatus.data)
                    }
                    // If the status is ERROR, fail the test with an appropriate message
                    DataStatus.Status.ERROR -> {
                        Assert.fail("Unexpected error state")
                    }
                }
            }
        }
}