package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubServerResponse
import jp.co.yumemi.android.code_check.network.GithubApiService
import jp.testdata.MockData.mockObject
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
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
                        mockObject
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
                assertTrue(true)
                // Check success state
                when (dataStatus.status) {
                    DataStatus.Status.LOADING -> {
                        assertTrue(dataStatus.data == null)
                    }
                    // If the status is SUCCESS, assert that the returned data matches the expected list.
                    DataStatus.Status.SUCCESS -> {
                        val expectedList = listOf(
                            mockObject
                        )
                        assertEquals(expectedList, dataStatus.data)
                    }
                    // If the status is ERROR, fail the test with an appropriate message
                    DataStatus.Status.ERROR -> {
                        fail("Unexpected error state")
                    }
                }
            }
        }

    /**
     * Test case for verifying the behavior of [GithubRepositoryImpl.getGithubAccountsFromDataSource] function
     * when the API call fails.
     */
    @Test
    fun `getGitHutAccountsFromDataSource should return loading and then error when API call fails`() =
        runBlocking {
            // Given
            val searchQuery = "kotlin"

            val mockErrorResponse = Response.error<GitHubServerResponse>(
                404,
                "".toResponseBody(
                    "application/json".toMediaType()
                )
            )

            // Mock unsuccessful response from GithubApiService
            `when`(mockGithubApiService.fetchRepositoryInformation(searchQuery)).thenReturn(
                mockErrorResponse
            )

            // When
            val flow = githubRepository.getGithubAccountsFromDataSource(searchQuery)

            // Then
            flow.collect { dataStatus ->
                // Check loading state
                assertTrue(true)

                // Check error state
                when (dataStatus.status) {
                    DataStatus.Status.LOADING -> {
                        assertTrue(dataStatus.data == null)
                    }

                    DataStatus.Status.SUCCESS -> {
                        // Fail the test if success state is encountered unexpectedly
                        fail("Expected error state but got success")
                    }

                    DataStatus.Status.ERROR -> {
                        // Assert that the error message is not null and matches the expected format
                        assertNotNull(dataStatus.message)
                        assertEquals(
                            "Failed to fetch repositories: ${mockErrorResponse.message()}",
                            dataStatus.message
                        )
                    }
                }
            }
        }
}