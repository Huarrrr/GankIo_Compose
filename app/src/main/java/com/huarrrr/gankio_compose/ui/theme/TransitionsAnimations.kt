package com.huarrrr.gankio_compose.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun FloatMultiStateAnimationCircleCanvas(color: Color = Colors.green, radiusEnd: Float = 100f) {
    Spacer(modifier = Modifier.height(100.dp))
    val transition = rememberInfiniteTransition()
    val floatAnim by transition.animateFloat(
        initialValue = 10f,
        targetValue = radiusEnd,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse)
    )

    Canvas(modifier = Modifier.padding(16.dp)) {
        val centerOffset = Offset(
            10f,
            10f
        )
        drawCircle(
            color = color.copy(alpha = 0.8f),
            radius = floatAnim,
            center = centerOffset,
        )
        drawCircle(
            color = color.copy(alpha = 0.4f),
            radius = floatAnim / 2,
            center = centerOffset,
        )
        drawCircle(
            color = color.copy(alpha = 0.2f),
            radius = floatAnim / 4,
            center = centerOffset,
        )
    }
    Spacer(modifier = Modifier.height(100.dp))
}