package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.DataStatus
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.network.GithubApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Implementation of the [GithubRepository] interface, responsible for retrieving GitHub accounts
 * from a data source using the provided [githubApiService].
 *
 * This class handles the retrieval of GitHub accounts based on a specified search query. It emits
 * [DataStatus] objects via a Flow to represent the status of the data operation, including loading,
 * success, or error states.
 *
 * @property githubApiService The service responsible for fetching repository information from the GitHub API.
 */
class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService
) : GithubRepository {

    /**
     * Retrieves GitHub accounts based on the specified search query.
     *
     * @param searchQuery The search query used to filter GitHub accounts.
     * @return A Flow emitting DataStatus objects containing the list of GitHub accounts.
     */
    override suspend fun getGithubAccountsFromDataSource(
        searchQuery: String
    ): Flow<DataStatus<List<GitHubAccount>>> {
        return flow {
            // Emit loading status
            emit(DataStatus.loading())

            // Call the API to fetch repository information
            val response = githubApiService.fetchRepositoryInformation(searchQuery)

            // Check if the response is successful
            if (response.isSuccessful) {
                // Parse the response body
                val serverResponse = response.body()
                val items = serverResponse?.items ?: emptyList()
                // Emit success status with the retrieved data
                emit(DataStatus.success(items))

            } else {
                // Emit error status with the error message from the response
                emit(DataStatus.error("Failed to fetch repositories: ${response.message()}"))
            }
        }.catch {
            // Emit error status with the exception message
            emit(DataStatus.error("Error fetching repositories: ${it.message}"))
        }.flowOn(Dispatchers.IO)
    }
}