package com.example.movieapp.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmer(cornerRadius: Dp = 0.dp): Modifier {
    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val colors = listOf(base, highlight.compositeOver(base), base)

    val progress by rememberInfiniteTransition(label = "Shimmer").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing)),
        label = "Progress"
    )

    return drawWithCache {
        val cornerPx = cornerRadius.toPx()
        onDrawBehind {
            val startX = -size.width + progress * size.width * 3f
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(startX, 0f),
                    end = Offset(startX + size.width, size.height)
                ),
                cornerRadius = CornerRadius(cornerPx, cornerPx)
            )
        }
    }
}