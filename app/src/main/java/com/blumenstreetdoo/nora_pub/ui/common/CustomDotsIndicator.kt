package com.blumenstreetdoo.nora_pub.ui.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomDotsIndicator(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    selectedDotSize: Dp = 12.dp,
    dotSpacing: Dp = 8.dp,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until pageCount) {
            val isSelected = i == currentPage
            val animatedSize by animateDpAsState(targetValue = if (isSelected) selectedDotSize else dotSize)
            val animatedColor = if (isSelected) selectedColor else unselectedColor
            val animatedScale by animateFloatAsState(if (isSelected) 1.2f else 1f)

            Box(
                modifier = Modifier
                    .size(animatedSize)
                    .scale(animatedScale)
                    .background(animatedColor, shape = CircleShape)
            )
        }
    }
}
