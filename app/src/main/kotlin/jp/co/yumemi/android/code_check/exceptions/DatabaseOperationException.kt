package jp.co.yumemi.android.code_check.exceptions

/**
 * Exception class representing database operation errors.
 *
 * @param message A description of the error.
 * @param cause The cause of the error, if available.
 */
class DatabaseOperationException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)