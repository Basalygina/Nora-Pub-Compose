package com.blumenstreetdoo.nora_pub.ui.news_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.ui.common.image.FullWidthBannerImage
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun NewsDetailsScreen(news: News) {
    Column {
        FullWidthBannerImage(
            imageUrl = news.imageUrl,
            modifier = Modifier.fillMaxWidth()
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))

            if (news.type == NewsType.NEW_ARRIVAL) {
                Text(
                    text = stringResource(id = R.string.new_arrival),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (!news.description.isNullOrBlank()) {
                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailsScreenPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        val sample = News(
            id = "1",
            title = "IIPPAA от Pinta Craft Beer",
            imageUrl = null,
            description = "News description\nLine 1\nLine 2\nLine 3",
            type = NewsType.NEW_ARRIVAL
        )
        NewsDetailsScreen(news = sample)
    }
}