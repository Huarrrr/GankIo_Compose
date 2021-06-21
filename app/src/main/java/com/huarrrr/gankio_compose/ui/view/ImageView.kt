package com.huarrrr.gankio_compose.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.huarrrr.gankio_compose.utils.ImageLoader


/**
 * ImageView
 *
 * @author Huarrrr on 2021/6/21
 */

@Composable
fun ImageViewer(navController: NavHostController, img: String) {
    Box(
        Modifier.fillMaxSize()
    ) {
        ImageLoader(modifier = Modifier
            .fillMaxSize().clickable {
            navController.popBackStack()
        }, data = img)
    }
}