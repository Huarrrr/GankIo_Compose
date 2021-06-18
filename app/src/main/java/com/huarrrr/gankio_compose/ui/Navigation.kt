package com.huarrrr.gankio_compose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.ui.web.Web

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { Main(navController) }

        composable("web?url={url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            Web(navController, url)
        }

        composable("image?json={json}") { it
            val json = it.arguments?.getString("json") ?: ""
            val subscription = Gson().fromJson(json,GankData::class.java)
            Web(navController, subscription.url)
        }

    }
}