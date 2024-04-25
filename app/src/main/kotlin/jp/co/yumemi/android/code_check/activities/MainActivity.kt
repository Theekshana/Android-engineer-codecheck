/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.activities

import androidx.appcompat.app.AppCompatActivity
import jp.co.yumemi.android.code_check.R
import java.util.Date

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        lateinit var lastSearchDate: Date
    }
}
