package com.blumenstreetdoo.nora_pub.ui.common.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blumenstreetdoo.nora_pub.R

@Composable
fun FullWidthBannerImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholderRes: Int = R.drawable.placeholder_nora_large,
    screenHeightFraction: Float = 0.4f,
    cornerRadiusDp: Int = 0
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight: Dp = screenHeight * screenHeightFraction

    val baseModifier = modifier
        .fillMaxWidth()
        .height(imageHeight)
        .clip(RoundedCornerShape(cornerRadiusDp.dp))

    if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .placeholder(placeholderRes)
                .error(placeholderRes)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = baseModifier
        )
    } else {
        Image(
            painter = painterResource(id = placeholderRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = baseModifier
        )
    }
}