package com.huarrrr.gankio_compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huarrrr.gankio_compose.R
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.ui.home.vm.HomeViewModel
import com.huarrrr.gankio_compose.ui.theme.Colors
import com.huarrrr.gankio_compose.ui.view.GankAppBar
import com.huarrrr.gankio_compose.ui.view.PageLoading
import com.huarrrr.gankio_compose.ui.view.RatingBar
import com.huarrrr.gankio_compose.ui.view.Toaster
import com.huarrrr.gankio_compose.ui.view.banner.BannerGravity
import com.huarrrr.gankio_compose.ui.view.banner.BannerPager
import com.huarrrr.gankio_compose.ui.view.banner.CircleIndicator
import com.huarrrr.gankio_compose.utils.ImageLoader

/**
 * Home
 *
 * @
 * Huarrrr on 2021/6/16
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
            loadState = viewModel.homePageState,
            onReload = {
                viewModel.loadBanner()
                viewModel.loadGank()
            }) {
            LazyColumn(Modifier.fillMaxWidth()) {
                item {
                    BannerView(navController, viewModel.bannerList)
                }
                item {
                    Text(
                        "—— 热门文章 ——",
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        Colors.text_h1,
                        17.sp,
                        textAlign = TextAlign.Center
                    )
                }

                itemsIndexed(viewModel.gankList) { index, item ->
                    GankItem(navController, item)
                }

                item {
                    Text(
                        "—— 热门妹子 ——",
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        Colors.text_h1,
                        17.sp,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    LazyRow(Modifier.padding(horizontal = 10.dp)) {
                        itemsIndexed(viewModel.girlList) { index, item ->
                            Box(
                                modifier = Modifier
                                    .width(160.dp)
                                    .padding(5.dp)
                            ) {
                                showImageCard(navController, item, title = item.desc, item.url)
                            }
                        }
                    }
                }


            }
        }
    }


}

@Composable
fun BannerView(navController: NavHostController, bannerList: List<BannerBean>) {
    if (!bannerList.isNullOrEmpty()) {
        BannerPager(
            items = bannerList,
            indicator = CircleIndicator(gravity = BannerGravity.BottomCenter)
        ) {
            navController.navigate("web?url=${it.url}")
        }
    }
}

@Composable
fun GankItem(navController: NavHostController, item: GankData) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable {
                Toaster.show("uuuuuu")
            },
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Surface(
            elevation = 1.dp
        )
        {
            Row {
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    val data: Any
                    data = if (item.images.size == 0) {
                        R.mipmap.img_no_photo
                    } else {
                        item.images[0]
                    }
                    ImageLoader(data = data)
                }

                Column(
                    Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            "热门",
                            Modifier
                                .align(Alignment.CenterVertically)
                                .border(0.5.dp, Color.Red, RoundedCornerShape(3.dp))
                                .padding(2.dp, 1.dp),
                            Color.Red,
                            10.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .width(8.dp)
                                .height(0.dp)
                        )
                        Text(
                            item.author,
                            Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            Colors.text_h2,
                            12.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .width(10.dp)
                                .height(0.dp)
                        )
                        Text(
                            item.createdAt,
                            Modifier
                                .align(Alignment.CenterVertically),
                            Colors.text_h2,
                            12.sp
                        )
                    }
                    Text(
                        item.desc,
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp).clickable {
                                navController.navigate("web?url=${item.url}")
                            },
                        Colors.text_h1,
                        14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        Text(
                            "分类·干货",
                            Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            Colors.text_h2,
                            12.sp,
                        )
                        RatingBar(rating = item.stars.toFloat(), Modifier.height(12.dp))
                    }
                }

            }


        }
    }
}

@Composable
fun showImageCard(navController: NavHostController, it: GankData, title: String, url: Any) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            ImageLoader(data = url)
            //渐变
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
                    .clickable {
                        if (it.type == "Girl") {
                            navController.navigate("image?image=${it.images[0]}")
                        } else {
                            navController.navigate("web?url=${it.url}")
                        }
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.BottomStart
            )
            {
                Text(title, maxLines = 2, style = TextStyle(color = Color.White, fontSize = 12.sp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        GankItem(
            rememberNavController(),
            item = GankData(
                title = "标题标题",
                createdAt = "2020-07-08 12:34:45",
                desc = "这是一个很长的描述",
                author = "作者",
                stars = 3
            )
        )
    }

}