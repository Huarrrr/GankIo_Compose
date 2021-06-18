package com.huarrrr.gankio_compose.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.huarrrr.gankio_compose.ui.theme.GankIo_ComposeTheme
import com.huarrrr.gankio_compose.utils.BarUtils
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(this)
        setAndroidNativeLightStatusBar()
        setContent {
            GankIo_ComposeTheme {
                ProvideWindowInsets {
                    ComposeNavigation()
                }
            }
        }
    }

    /**
     * 状态栏反色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAndroidNativeLightStatusBar() {
        val decor = window.decorView
        val isDark = resources.configuration.uiMode == 0x21
        if (!isDark) {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}


