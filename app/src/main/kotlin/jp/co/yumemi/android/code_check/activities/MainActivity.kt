/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.ActivityMainBinding
import jp.co.yumemi.android.code_check.util.NetworkUtils
import java.util.Date
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSystemService(Context.CONNECTIVITY_SERVICE)
            .let { it as ConnectivityManager }
            .let { NetworkUtils.init(it) }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }


}
