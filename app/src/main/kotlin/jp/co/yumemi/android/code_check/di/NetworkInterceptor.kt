package jp.co.yumemi.android.code_check.di

import jp.co.yumemi.android.code_check.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor responsible for adding custom headers to network requests.
 */
class NetworkInterceptor @Inject constructor() : Interceptor {

    /**
     * Intercepts the network request and adds a custom header before proceeding with the request.
     * @param chain The interceptor chain.
     * @return The response from the server.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request from the chain
        val request = chain.request().newBuilder()
            // Add a custom header to the request
            .addHeader("Accept", Constants.HEADER)
            .build()

        // Proceed with the modified request and return the response
        return chain.proceed(request)
    }
}