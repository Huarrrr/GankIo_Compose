package com.huarrrr.gankio_compose.ui.gank

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huarrrr.gankio_compose.R
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.ui.gank.vm.GankViewModel
import com.huarrrr.gankio_compose.ui.home.showImageCard
import com.huarrrr.gankio_compose.ui.theme.Colors
import com.huarrrr.gankio_compose.ui.view.GankAppBar
import com.huarrrr.gankio_compose.ui.view.PageLoading
import com.huarrrr.gankio_compose.ui.view.SwipeRefreshAndLoadLayout


/**
 * Gank
 *
 * @author Huarrrr on 2021/6/17
 */

var type by mutableStateOf("Android")
var category by mutableStateOf("GanHuo")

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun Gank(navController: NavHostController) {
    val viewModel: GankViewModel = viewModel()

    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.white),
    ) {
        GankAppBar(
            stringResource(id = R.string.gank_page),
            false
        )
        TypeTabRow(navController, viewModel)
    }
}

@ExperimentalFoundationApi
@Composable
fun TypeTabRow(navController: NavHostController, viewModel: GankViewModel) {
    val state = remember { mutableStateOf(0) }
    val titles = listOf("Android", "iOS", "Flutter", "前端", "APP", "妹纸")
    val types = listOf("Android", "iOS", "Flutter", "frontend", "app", "Girl")
    Column {
        ScrollableTabRow(
            selectedTabIndex = state.value,
            modifier = Modifier.wrapContentWidth(),
            edgePadding = 16.dp,
            backgroundColor = Colors.background
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state.value == index,
                    onClick = { state.value = index
                        if (state.value == 5) {
                            category = "Girl"
                            type = "Girl"
                        } else {
                            category = "GanHuo"
                            type = types[state.value]
                        }
                        viewModel.lazyLoad(category, type)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.lazyLoad(category, type) }) {
            SwipeRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh(category, type) },
                onLoad = { viewModel.onLoadMore(category, type) }) {
                showGankList(navController, viewModel.list)
            }
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun showGankList(navController: NavHostController, list: MutableList<GankData>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(list) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(5.dp)
            ) {
                if (it.images.isNullOrEmpty()) {
                    showImageCard(navController, it, title = it.desc, R.mipmap.img_no_photo)
                } else {
                    showImageCard(navController, it, title = it.desc, it.images[0])
                }
            }

        }
    }
}

