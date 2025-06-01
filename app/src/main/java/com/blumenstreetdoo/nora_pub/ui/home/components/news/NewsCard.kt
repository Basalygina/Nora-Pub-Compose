package com.blumenstreetdoo.nora_pub.ui.home.components.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun NewsCard(
    news: News,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.placeholder_nora)
                    .error(R.drawable.placeholder_nora)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                if (news.type == NewsType.NEW_ARRIVAL) {
                    Text(
                        text = stringResource(id = R.string.new_arrival),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        NewsCard(
            news = News(
                id = "1",
                title = "New seasonal beer just arrived at the pub!",
                imageUrl = null,
                type = NewsType.NEW_ARRIVAL
            ),
            onClick = {}
        )
    }
}
