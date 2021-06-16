package com.huarrrr.gankio_compose.ui.view.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huarrrr.gankio_compose.model.BannerBean
import com.huarrrr.gankio_compose.model.BaseBannerBean
import com.huarrrr.gankio_compose.ui.view.CompostablePosition
import com.huarrrr.gankio_compose.ui.view.compostablePosition
import com.huarrrr.gankio_compose.utils.ImageLoader

/**
 * BannerCard
 *
 * @author Huarrrr on 2021/6/16
 */

@Composable
fun BannerCard(
    bean: BannerBean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(10.dp),
    contentScale: ContentScale,
    onBannerClick: () -> Unit,
) {
    if (bean.data == null) {
        throw NullPointerException("Url or imgRes or filePath must have a not for empty.")
    }

    Box {
        Card(
            shape = shape,
            modifier = modifier
        ) {
            val imgModifier = Modifier.clickable(onClick = onBannerClick)
            ImageLoader(bean.data, imgModifier, contentScale)
        }
        Box(
            modifier = Modifier
                .compostablePosition(CompostablePosition.CenterBottom)
        )
        {
            Text(
                text = bean.title,
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .background(
                        Color(0x33CCCCCC),
                        shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                    ),
                fontSize = 14.sp,
                color = Color.LightGray,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }

}