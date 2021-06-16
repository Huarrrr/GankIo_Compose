package com.huarrrr.gankio_compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huarrrr.gankio_compose.R
import com.huarrrr.gankio_compose.model.ArticleListData
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.ui.home.vm.HomeViewModel
import com.huarrrr.gankio_compose.ui.theme.Colors
import com.huarrrr.gankio_compose.ui.view.GankAppBar
import com.huarrrr.gankio_compose.ui.view.PageLoading
import com.huarrrr.gankio_compose.ui.view.SwipeRefreshAndLoadLayout
import com.huarrrr.gankio_compose.ui.view.Toaster
import com.huarrrr.gankio_compose.ui.view.banner.BannerGravity
import com.huarrrr.gankio_compose.ui.view.banner.BannerPager
import com.huarrrr.gankio_compose.ui.view.banner.CircleIndicator

/**
 * Home
 *
 * @author Huarrrr on 2021/6/16
 */
@ExperimentalPagerApi
@Composable
fun Home(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        GankAppBar(
            stringResource(id = R.string.home_page),
            false
        )
        PageLoading(
            loadState = viewModel.pageState,
            showLoading = viewModel.showLoading,
            onReload = { viewModel.firstLoad() }) {
            SwipeRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh() },
                onLoad = { viewModel.onLoad() }) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Colors.white)
                ) {
                    itemsIndexed(viewModel.list) { index, item ->
                        if (item is List<*>) {
                            val items = item as List<BannerBean>
                            BannerPager(
                                items = items,
                                indicator = CircleIndicator(gravity = BannerGravity.BottomCenter)
                            ) { it ->
                                Toaster.show(it.title)
                            }

                        } else if (item is ArticleListData) {
                            ArticleItem(navController, item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    navController: NavHostController,
    article: ArticleListData
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        arrayListOf(
                            colorResource(R.color.article_item_bg_start),
                            colorResource(R.color.article_item_bg_mid),
                            colorResource(R.color.article_item_bg_end)
                        )
                    )
                )
                .clickable {
                    navController.navigate("web?url=${article.url}")
                }

        ) {
            Column(
                Modifier.padding(16.dp, 10.dp)
            ) {
                //标题
                Text(
                    article.title,
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    Colors.text_h1,
                    15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    //作者
                    Text(
                        article.author,
                        Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        Colors.text_h2,
                        12.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        rememberNavController(),
        article = ArticleListData(title = "标题标题标题标题标题", author = "作者")
    )

}