package com.huarrrr.gankio_compose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.huarrrr.gankio_compose.ui.gank.Gank
import com.huarrrr.gankio_compose.ui.home.Home
import com.huarrrr.gankio_compose.ui.profile.Profile
import com.huarrrr.gankio_compose.ui.view.BottomTab
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun Main(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = 3, initialOffscreenLimit = 2)
        HorizontalPager(pagerState, Modifier.weight(1f),dragEnabled = false) {
            when (currentPage) {
                0 -> {
                    Home(navController)
                }

                1 -> {
                    Gank(navController)
                }

                2 -> {
                    Profile()
                }
            }
        }
        BottomTab(pagerState.currentPage) {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }
    }
}