package jp.testdata

import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.model.Owner

/**
 * A singleton object providing mock data for testing purposes.
 */
object MockData {
    val mockObject = GitHubAccount(
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
}