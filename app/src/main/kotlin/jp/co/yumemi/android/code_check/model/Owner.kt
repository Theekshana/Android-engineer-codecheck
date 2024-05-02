package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents the owner of a GitHub repository.
 *
 * @property avatarUrl The URL of the owner's avatar.
 * @property htmlUrl The URL of the owner's GitHub profile.
 */
@Parcelize
data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
) : Parcelable
