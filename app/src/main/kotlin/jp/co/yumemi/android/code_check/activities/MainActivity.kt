/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.Constants.SPLASH_SCREEN_TIME
import jp.co.yumemi.android.code_check.databinding.ActivityMainBinding
import jp.co.yumemi.android.code_check.util.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * The main activity of the application.
 * This activity hosts the navigation graph and sets up navigation components.
 * It also initializes necessary components like data binding and connectivity manager.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Data binding instance for the activity layout
    private lateinit var binding: ActivityMainBinding

    // Configuration for the app bar and navigation controller
    private lateinit var appBarConfiguration: AppBarConfiguration

    // Navigation controller instance to navigate between destinations
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            installSplashScreen()
            delay(SPLASH_SCREEN_TIME)
        }

        // Initialize NetworkUtils with ConnectivityManager instance
        getSystemService(Context.CONNECTIVITY_SERVICE)
            .let { it as ConnectivityManager }
            .let { NetworkUtils.init(it) }

        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set up navigation controller
        val newsNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = newsNavHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Set up action bar with navigation controller
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Listen for destination changes to update bottom navigation view
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment -> {
                    // Highlight Home menu item when on HomeFragment
                    binding.bottomNavigationView.menu.findItem(R.id.miHome)?.isChecked = true
                }

                R.id.favouritesFragment -> {
                    // Highlight Favourite menu item when on FavouritesFragment
                    binding.bottomNavigationView.menu.findItem(R.id.miFavourite)?.isChecked = true
                }

                R.id.appInfoFragment -> {
                    // Highlight About menu item when on AppInfoFragment
                    binding.bottomNavigationView.menu.findItem(R.id.miAbout)?.isChecked = true
                }

            }
        }

        // Set listener for bottom navigation view item selection
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miHome -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                    true
                }

                R.id.miFavourite -> {
                    if (navController.currentDestination?.id != R.id.favouritesFragment) {
                        navController.navigate(R.id.favouritesFragment)
                    }
                    true
                }

                R.id.miAbout -> {
                    if (navController.currentDestination?.id != R.id.appInfoFragment) {
                        navController.navigate(R.id.appInfoFragment)
                    }
                    true
                }

                else -> false
            }
        }

    }

    /**
     * Handles Up navigation.
     * @return True if Up navigation was handled successfully, false otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}
