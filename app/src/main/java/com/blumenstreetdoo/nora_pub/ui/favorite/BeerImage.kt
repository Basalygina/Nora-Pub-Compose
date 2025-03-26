package com.blumenstreetdoo.nora_pub.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BeerImage(
    imageUrl: String?,
    placeholderRes: Int,
    modifier: Modifier = Modifier,
    imageSize: Int = 60,
    startPadding: Int = 12,
    endPadding: Int = 0,
    topPadding: Int = 0,
    bottomPadding: Int = 0,
    cornerRadius: Int = 8,
    contentScale: ContentScale = ContentScale.Crop,
    ) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .size(imageSize.dp)
                .padding(start = startPadding.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
        ) {
            it.error(placeholderRes)
                .placeholder(placeholderRes)
        }
    } else {
        Image(
            painter = painterResource(id = placeholderRes),
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .size(imageSize.dp)
                .padding(start = startPadding.dp, top = topPadding.dp, bottom = bottomPadding.dp, end = endPadding.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
        )
    }
}