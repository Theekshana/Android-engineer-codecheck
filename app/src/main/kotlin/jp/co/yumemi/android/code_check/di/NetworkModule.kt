package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.constants.Constants
import jp.co.yumemi.android.code_check.constants.Constants.NETWORK_TIMEOUT
import jp.co.yumemi.android.code_check.network.GithubApiService
import jp.co.yumemi.android.code_check.repository.GithubRepository
import jp.co.yumemi.android.code_check.repository.GithubRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module responsible for providing network-related dependencies such as base URL,
 * OkHttpClient, Retrofit instance, and API service interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the base URL for the network requests.
     * @return The BASE_URL as a String.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Constants.BASE_URL
    }

    /**
     * Provides the network timeout value.
     */
    @Provides
    fun provideNetworkTimeout() = NETWORK_TIMEOUT

    /**
     * Provides the Gson converter factory for JSON serialization/deserialization.
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides the HTTP logging interceptor for logging network requests and responses.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Provides the OkHttpClient instance with specified interceptors and timeouts.
     * @param loggingInterceptor The logging interceptor.
     * @param headerInterceptor The header interceptor.
     * @param timeout The timeout value in seconds.
     * @return The configured OkHttpClient instance.
     */
    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: NetworkInterceptor,
        timeout: Long
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * Provides the Retrofit instance for network operations.
     * @param baseUrl The base URL for the Retrofit instance.
     * @param okHttpClient The OkHttpClient instance.
     * @param converterFactory The converter factory for serialization/deserialization.
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides the Github API service interface.
     * @param retrofit The Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }

    /**
     * Provides the repository for Github API operations.
     * @param githubApiService The Github API service interface.
     */
    @Singleton
    @Provides
    fun provideGithubRepository(githubApiService: GithubApiService): GithubRepository {
        return GithubRepositoryImpl(githubApiService)
    }
}