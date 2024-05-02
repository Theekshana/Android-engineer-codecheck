package jp.co.yumemi.android.code_check.network

import jp.co.yumemi.android.code_check.constants.Constants.END_POINT_REPOSITORIES
import jp.co.yumemi.android.code_check.model.GitHubServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface representing the GitHub API service.
 */
interface GithubApiService {

    /**
     * Retrieves a response containing repository information from the GitHub API.
     * @param q The query string to search for repositories.
     * @return A Response object containing the server response.
     */
    @GET(END_POINT_REPOSITORIES)
    suspend fun fetchRepositoryInformation(
        @Query("q") q: String,
    ): Response<GitHubServerResponse>

}