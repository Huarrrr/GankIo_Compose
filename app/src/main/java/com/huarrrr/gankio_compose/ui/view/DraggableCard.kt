package com.huarrrr.gankio_compose.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huarrrr.gankio_compose.model.GankData
import com.huarrrr.gankio_compose.ui.girl.SwipeResult
import com.huarrrr.gankio_compose.ui.theme.Colors
import com.huarrrr.gankio_compose.ui.theme.Tomato
import com.huarrrr.gankio_compose.utils.ImageLoader
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * DraggableCard
 *
 * @author Huarrrr on 2021/6/17
 */

@Composable
fun DraggableCard(
    item: GankData,
    modifier: Modifier = Modifier,
    onTouch:(String) -> Unit,
    onSwiped: (Any, GankData) -> Unit,
    onFavorite:(GankData) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val swipeXLeft = -(screenWidth.value * 3.2).toFloat()
    val swipeXRight = (screenWidth.value * 3.2).toFloat()
    val swipeYTop = -1000f
    val swipeYBottom = 1000f
    val swipeX = remember { Animatable(0f) }
    val swipeY = remember { Animatable(0f) }
    swipeX.updateBounds(swipeXLeft, swipeXRight)
    swipeY.updateBounds(swipeYTop, swipeYBottom)
    val rotationFraction = (swipeX.value / 60).coerceIn(-40f, 40f)
    val graphicLayer = Modifier.graphicsLayer(
        translationX = swipeX.value,
        translationY = swipeY.value,
        rotationZ = rotationFraction,
    )
    if (abs(swipeX.value) < swipeXRight - 50f) {
        Card(
            elevation = 5.dp,
            modifier = modifier
                .dragContent(
                    swipeX = swipeX,
                    swipeY = swipeY,
                    maxX = swipeXRight
                )
                .then(graphicLayer)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column {
                ImageLoader(
                    data = item.url,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth().clickable {
                            onTouch(item.url)
                        }
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp,start = 8.dp, end = 8.dp),
                    maxLines = 2
                )
                Text(
                    text = item.desc,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = 8.dp,end = 8.dp),
                    textAlign = TextAlign.Start
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            onFavorite(item)
                        },
                        modifier = Modifier
                            .padding(15.dp)
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(Colors.bg)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Tomato,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    } else {
        val swipeResult = if (swipeX.value > 0) SwipeResult.ACCEPTED else SwipeResult.REJECTED
        onSwiped(swipeResult, item)
    }
}

fun Modifier.dragContent(
    swipeX: Animatable<Float, AnimationVector1D>,
    swipeY: Animatable<Float, AnimationVector1D>,
    maxX: Float
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    pointerInput(Unit) {
        this.detectDragGestures(
            onDragCancel = {
                coroutineScope.apply {
                    launch { swipeX.animateTo(0f) }
                    launch { swipeY.animateTo(0f) }
                }
            },
            onDragEnd = {
                coroutineScope.apply {
                    // if it's swiped 1/4th
                    if (abs(swipeX.targetValue) < abs(maxX) / 4) {
                        launch {
                            swipeX.animateTo(0f, tween(400))
                        }
                        launch {
                            swipeY.animateTo(0f, tween(400))
                        }
                    } else {
                        launch {
                            if (swipeX.targetValue > 0) {
                                swipeX.animateTo(maxX, tween(400))
                            } else {
                                swipeX.animateTo(-maxX, tween(400))
                            }
                        }
                    }
                }
            }
        ) { change, dragAmount ->
            change.consumePositionChange()
            coroutineScope.apply {
                launch { swipeX.animateTo(swipeX.targetValue + dragAmount.x) }
                launch { swipeY.animateTo(swipeY.targetValue + dragAmount.y) }
            }
        }
    }
}