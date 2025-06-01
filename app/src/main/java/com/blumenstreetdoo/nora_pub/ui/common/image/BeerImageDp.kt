package com.blumenstreetdoo.nora_pub.ui.common.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blumenstreetdoo.nora_pub.R

@Composable
fun BeerImageDp(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    placeholderRes: Int = R.drawable.placeholder_nora_large,
    contentScale: ContentScale = ContentScale.Crop,
    imageSize: Int = 60,
    cornerRadius: Int = 8,
    paddingStart: Int = 0,
    paddingTop: Int = 0,
    ) {
    if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .placeholder(placeholderRes)
                .error(placeholderRes)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .size(imageSize.dp)
                .padding(start = paddingStart.dp, top = paddingTop.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
        )
    } else {
        Image(
            painter = painterResource(id = placeholderRes),
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
                .size(imageSize.dp)
                .padding(start = paddingStart.dp, top = paddingTop.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
        )
    }

}