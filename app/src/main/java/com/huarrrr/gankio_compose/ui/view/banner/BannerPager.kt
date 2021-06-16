package com.huarrrr.gankio_compose.ui.view.banner

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.model.BaseBannerBean
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "BannerPager"

/**
 * BannerPager
 *
 * @author Huarrrr on 2021/6/16
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T : BaseBannerBean> BannerPager(
    modifier: Modifier = Modifier,
    items: List<T> = arrayListOf(),
    config: BannerConfig = BannerConfig(),
    indicator: Indicator = CircleIndicator(),
    onBannerClick: (T) -> Unit
) {
    if (items.isEmpty()) {
        throw NullPointerException("items is not null")
    }

    val pagerState = rememberPagerState(pageCount = items.size)

    if (config.repeat) {
        startBanner(pagerState, config.intervalTime)
    }

    Box(modifier = modifier.height(config.bannerHeight)) {
        HorizontalPager(state = pagerState) { page ->
            val item = items[page]
            BannerCard(
                bean = item as BannerBean,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        config.bannerImagePadding * 2,
                        config.bannerImagePadding,
                        config.bannerImagePadding * 2,
                        config.bannerImagePadding * 5
                    ),
                shape = config.shape,
                contentScale = config.contentScale
            ) {
                Log.d(TAG, "item is :${item.javaClass}")
                onBannerClick(item)
            }
        }

        indicator.DrawIndicator(pagerState)
    }
}

var mTimer: Timer? = null
var mTimerTask: TimerTask? = null


@OptIn(ExperimentalPagerApi::class)
@Composable
fun startBanner(pagerState: PagerState, intervalTime: Long) {
    val coroutineScope = rememberCoroutineScope()
    mTimer?.cancel()
    mTimerTask?.cancel()
    mTimer = Timer()
    mTimerTask = object : TimerTask() {
        override fun run() {
            coroutineScope.launch {
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
            }
        }
    }
    mTimer?.schedule(mTimerTask, intervalTime, intervalTime)
}