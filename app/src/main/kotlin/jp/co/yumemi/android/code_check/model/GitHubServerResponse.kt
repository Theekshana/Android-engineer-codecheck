package jp.co.yumemi.android.code_check.model

/**
 * Data class representing the server response containing a list of GitHub accounts.
 * @property items The list of GitHub accounts returned in the response.
 */
data class GitHubServerResponse(
    val items: List<GitHubAccount>
)