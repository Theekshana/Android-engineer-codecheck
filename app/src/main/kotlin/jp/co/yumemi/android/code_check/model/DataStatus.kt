package jp.co.yumemi.android.code_check.model

/**
 * Represents the status of a data operation, including loading, success, or error states.
 *
 * @param T The type of data associated with the status.
 * @property status The status of the data operation.
 * @property data The optional data associated with the operation, available in success state.
 * @property message The optional error message, available in error state.
 */
data class DataStatus<out T>(
    // The status of the data operation (LOADING, SUCCESS, ERROR).
    val status: Status,
    // Optional data associated with the operation, available in success state.
    val data: T? = null,
    // Optional error message, available in error state.
    val message: String? = null
) {
    enum class Status {
        // Indicates that the data operation is in progress.
        LOADING,

        // Indicates that the data operation was successful.
        SUCCESS,

        // Indicates that an error occurred during the data operation.
        ERROR,
    }

    companion object {
        /**
         * Creates a DataStatus instance representing a loading state.
         */
        fun <T> loading(): DataStatus<T> {
            return DataStatus(Status.LOADING)
        }

        /**
         * Creates a DataStatus instance representing a success state with optional data.
         *
         * @param data The data associated with the success state.
         */
        fun <T> success(data: T?): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data)
        }

        /**
         * Creates a DataStatus instance representing an error state with an error message.
         *
         * @param error The error message describing the error state.
         */
        fun <T> error(error: String): DataStatus<T> {
            return DataStatus(Status.ERROR, message = error)
        }
    }
}

