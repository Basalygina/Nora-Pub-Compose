package com.blumenstreetdoo.nora_pub.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BeerImageScreenPerc(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    placeholderRes: Int = R.drawable.placeholder_nora_large,
    screenPercentage: Float = 0.4f,
    contentScale: ContentScale = ContentScale.Crop,
    padding: Int = 0,
    cornerRadius: Int = 0,
    ) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val imageSize = screenWidthDp * screenPercentage

    if (!imageUrl.isNullOrEmpty()) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .size(imageSize)
                .padding(padding.dp)
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
                .size(imageSize)
                .padding(padding.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
        )
    }
}