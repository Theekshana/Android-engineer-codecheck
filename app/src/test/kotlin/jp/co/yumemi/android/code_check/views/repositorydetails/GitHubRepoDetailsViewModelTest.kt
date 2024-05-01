package jp.co.yumemi.android.code_check.views.repositorydetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.testdata.MockData.mockObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for the [GitHubRepoDetailsViewModel] class.
 */
@RunWith(MockitoJUnitRunner::class)
class GitHubRepoDetailsViewModelTest {

    // Rule to make LiveData work synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Observer for observing changes in the repository details LiveData
    @Mock
    private lateinit var repositoryDetailsObserver: Observer<GitHubAccount?>

    // ViewModel to be tested
    private lateinit var viewModel: GitHubRepoDetailsViewModel

    /**
     * Set up the test environment.
     */
    @Before
    fun setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this)

        // Create an instance of the ViewModel
        viewModel = GitHubRepoDetailsViewModel()

        // Observe changes in the repository details LiveData
        viewModel.repositoryDetails.observeForever(repositoryDetailsObserver)
    }

    /**
     * Test to verify that loading repository details updates LiveData with the expected data.
     */
    @Test
    fun `testLoadRepositoryList updates LiveData with repository details`() {

        // Call the function to load the repository data
        viewModel.loadRepositoryDetails(mockObject)

        // Verify that the LiveData was updated with the expected repository data
        verify(repositoryDetailsObserver).onChanged(mockObject)
        assertEquals(mockObject, viewModel.repositoryDetails.value)

    }

    /**
     * Test to verify that loading repository details with a null repository
     * updates the LiveData to null.
     */
    @Test
    fun `testLoadRepositoryList null repository`() {

        // Call the function to load the repository data with null repository
        viewModel.loadRepositoryDetails(null)

        // Then verify that the repository details LiveData is null after loading with null input
        assertNull(viewModel.repositoryDetails.value)
    }
}