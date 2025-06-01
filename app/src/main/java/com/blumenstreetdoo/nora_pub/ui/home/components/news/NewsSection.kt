package com.blumenstreetdoo.nora_pub.ui.home.components.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun NewsSection(
    newsList: List<News>,
    onNewsClick: (News) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 8.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.latest_news),
                style = MaterialTheme.typography.titleLarge
            )
            if (newsList.size > 4) {
                Text(
                    text = stringResource(id = R.string.see_all_news),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .clickable(onClick = onSeeAllClick)
                        .padding(4.dp)
                )
            }
        }

        Column {
            newsList.take(4).forEachIndexed { index, news ->
                NewsCard(news = news, onClick = { onNewsClick(news) })
                if (index < minOf(3, newsList.size - 2)) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsSectionPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        val sampleNews = listOf(
            News(
                id = "1",
                title = "New seasonal beer is here!",
                imageUrl = "https://example.com/beer1.jpg",
                type = NewsType.NEW_ARRIVAL
            ),
            News(
                id = "2",
                title = "Live Music Fridays are back!",
                imageUrl = "https://example.com/music.jpg",
                type = NewsType.OTHER_NEWS
            ),
            News(
                id = "3",
                title = "Exclusive tap takeover this weekend!",
                imageUrl = "",
                type = NewsType.NEW_ARRIVAL
            ),
            News(
                id = "4",
                title = "Live Music Fridays are back!",
                imageUrl = "https://example.com/music.jpg",
                type = NewsType.OTHER_NEWS
            ),
            News(
                id = "5ё",
                title = "Exclusive tap takeover this weekend!",
                imageUrl = "",
                type = NewsType.NEW_ARRIVAL
            )
        )

        NewsSection(
            newsList = sampleNews,
            onNewsClick = {},
            onSeeAllClick = {},
        )
    }
}
