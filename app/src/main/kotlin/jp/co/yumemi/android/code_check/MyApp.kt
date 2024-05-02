package jp.co.yumemi.android.code_check

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class for initializing Hilt.
 * Annotating with [@HiltAndroidApp]
 * allows Hilt to generate the necessary components to enable dependency injection.
 */
@HiltAndroidApp
class MyApp : Application()
