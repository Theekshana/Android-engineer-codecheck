package jp.co.yumemi.android.code_check.constants

/**
 * This object contains constant values related to API endpoints and other configuration details.
 */
object Constants {

    /**
     * Base URL for the GitHub API requests.
     */
    const val BASE_URL = "https://api.github.com/search/"

    /**
     * Network timeout value in seconds.
     */
    const val NETWORK_TIMEOUT = 5L

    /**
     * Header value for specifying JSON format in requests.
     */
    const val HEADER = "application/vnd.github.v3+json"

    /**
     * This endpoint is used to fetch information about repositories.
     */
    const val END_POINT_REPOSITORIES = "repositories"

    /**
     * Key used for passing message data.
     */
    const val KEY_MESSAGE = "message"

    /**
     * Name of the table in the database that stores GitHub repository data.
     */
    const val GITHUB_REPOSITORY_TABLE = "github_repository"

    /**
     * Constant representing the name of the Room database for GitHub repositories.
     * This database stores information related to GitHub repositories.
     */
    const val GITHUB_REPOSITORY_DB_NAME = "github_repository_database"


}