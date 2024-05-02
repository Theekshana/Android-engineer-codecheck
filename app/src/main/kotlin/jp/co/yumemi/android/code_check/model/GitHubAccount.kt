package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import jp.co.yumemi.android.code_check.constants.Constants.GITHUB_REPOSITORY_TABLE
import kotlinx.parcelize.Parcelize

/**
 * Represents a GitHub repository account.
 *
 * @property id The unique identifier of the repository.
 * @property name The full name of the GitHub repository account.
 * @property owner The owner of the GitHub repository.
 * @property language The programming language used in the repository.
 * @property stargazersCount The number of stars for the repository.
 * @property watchersCount The number of watchers for the repository.
 * @property forksCount The number of forks for the repository.
 * @property openIssuesCount The number of open issues for the repository.
 */
@Entity(tableName = GITHUB_REPOSITORY_TABLE)
@Parcelize
data class GitHubAccount(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @SerializedName("full_name")
    val name: String?,
    val owner: Owner?,
    @SerializedName("language")
    val repositoryLanguage: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Long?,
    @SerializedName("watchers_count")
    val watchersCount: Long?,
    @SerializedName("forks_count")
    val forksCount: Long?,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long?,

    ) : Parcelable {

    /**
     * Gets the language of the GitHub repository.
     *
     * This property returns the language of the GitHub repository if available. If the language
     * is not available (i.e., if [repositoryLanguage] is null), it returns "No data" as a default value.
     *
     * @return The language of the GitHub repository, or "No data" if not available.
     */
    val language: String
        get() = repositoryLanguage ?: "No data"

}


