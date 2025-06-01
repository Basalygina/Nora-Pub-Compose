package com.blumenstreetdoo.nora_pub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.ui.home.components.AboutSection
import com.blumenstreetdoo.nora_pub.ui.home.components.HomeActionButtons
import com.blumenstreetdoo.nora_pub.ui.home.components.events.EventsCarousel
import com.blumenstreetdoo.nora_pub.ui.home.components.news.NewsSection
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun HomeContent(
    events: List<Event>,
    news: List<News>,
    onEventClick: (Event) -> Unit,
    onNewsClick: (News) -> Unit,
    onMenuClick: () -> Unit,
    onUntappdClick: () -> Unit,
    onPhoneClick: (String) -> Unit,
    onDirectionsClick: () -> Unit,
    onInstagramClick: () -> Unit,
    onSeeAllNewsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            EventsCarousel(
                events = events,
                onEventClick = onEventClick
            )
        }

        item {
            HomeActionButtons(
                onMenuClick = onMenuClick,
                onUntappdClick = onUntappdClick,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            NewsSection(
                newsList = news,
                onNewsClick = onNewsClick,
                modifier = Modifier.padding(horizontal = 16.dp),
                onSeeAllClick = onSeeAllNewsClick
            )
        }

        item {
            AboutSection(
                onPhoneClick = onPhoneClick,
                onDirectionsClick = onDirectionsClick,
                onInstagramClick = onInstagramClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val mockEvents = listOf(
        Event(
            id = "1",
            title = "Party Night",
            description = "Live music",
            dateTime = "Every Friday",
            imageUrl = "",
            type = EventType.PARTY
        )
    )
    val mockNews = listOf(
        News(
            id = "1",
            title = "New Beer Arrived!",
            imageUrl = "",
            type = NewsType.NEW_ARRIVAL
        )
    )

    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        HomeContent(
            events = mockEvents,
            news = mockNews,
            onEventClick = {},
            onNewsClick = {},
            onMenuClick = {},
            onUntappdClick = {},
            onPhoneClick = {},
            onDirectionsClick = {},
            onInstagramClick = {},
            onSeeAllNewsClick = {}
        )
    }
}
