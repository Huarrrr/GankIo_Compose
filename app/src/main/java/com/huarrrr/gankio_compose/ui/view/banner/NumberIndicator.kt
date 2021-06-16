package com.huarrrr.gankio_compose.ui.view.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

/**
 * NumberIndicator
 *
 * @author Huarrrr on 2021/6/16
 * 数字指示器，显示数字的指示器
 *
 * @param backgroundColor 数字指示器背景颜色
 * @param numberColor 数字的颜色
 * @param circleSize 背景圆的半径
 * @param fontSize 页码文字大小
 * @param gravity 指示器位置
 */
class NumberIndicator(
    var backgroundColor: Color = Color(30, 30, 33, 90),
    var numberColor: Color = Color.White,
    var circleSize: Dp = 35.dp,
    var fontSize: TextUnit = 15.sp,
    override var gravity: Int = BannerGravity.BottomRight,
) : Indicator() {

    @ExperimentalPagerApi
    @Composable
    override fun DrawIndicator(pagerState: PagerState) {
        val alignment: Alignment = when (gravity) {
            BannerGravity.BottomCenter -> {
                Alignment.BottomCenter
            }
            BannerGravity.BottomLeft -> {
                Alignment.BottomStart
            }
            BannerGravity.BottomRight -> {
                Alignment.BottomEnd
            }
            else -> Alignment.BottomEnd
        }
        Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = alignment) {
            Box(
                modifier = Modifier.size(circleSize).clip(CircleShape)
                    .background(color = backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                    color = numberColor,
                    fontSize = fontSize
                )
            }
        }
    }

}