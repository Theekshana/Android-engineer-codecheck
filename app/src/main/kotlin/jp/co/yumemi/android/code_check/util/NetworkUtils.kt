package jp.co.yumemi.android.code_check.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

/**
 * Utility class for checking network connectivity.
 */
class NetworkUtils @Inject constructor() {

    companion object {
        // Instance of ConnectivityManager to manage network connectivity.
        private var connectivityManager: ConnectivityManager? = null

        /**
         * Initializes the NetworkUtils with the given ConnectivityManager.
         * This should be called before using any other methods.
         * @param connectivityManager The ConnectivityManager instance.
         */
        fun init(connectivityManager: ConnectivityManager) {
            this.connectivityManager = connectivityManager
        }

        /**
         * Checks if the device has an active network connection.
         * @return true if network is available, false otherwise.
         */
        fun isNetworkAvailable(): Boolean {
            // Check if the connectivityManager is initialized
            return connectivityManager?.let { manager ->
                // Check if the active network has capabilities for cellular, wifi, or ethernet transport
                manager.getNetworkCapabilities(manager.activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
                // Return false if connectivityManager is null
            } ?: false
        }
    }
}