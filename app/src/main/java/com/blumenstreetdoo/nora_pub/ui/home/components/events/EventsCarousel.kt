package com.blumenstreetdoo.nora_pub.ui.home.components.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.ui.common.CustomDotsIndicator
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import kotlin.math.absoluteValue

@Composable
fun EventsCarousel(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    if (events.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { events.size })

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val eventSectionHeight = screenHeight * 0.55f

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(eventSectionHeight)
        ) { page ->
            val pageOffset = (
                    (pagerState.currentPage - page) +
                            pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                EventCard(
                    event = events[page],
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onEventClick(events[page]) }
                )
            }
        }

        CustomDotsIndicator(
            currentPage = pagerState.currentPage,
            pageCount = events.size,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventsCarouselPreview() {
    val mockEvents = listOf(
        Event(
            id = "1",
            title = "Party Night",
            description = "Join us for an amazing party night with live music!",
            dateTime = "Every Friday",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/PModern.webp",
            type = EventType.PARTY
        ),
        Event(
            id = "2",
            title = "Craft Beer Day",
            description = "Celebrate craft beer with us!",
            dateTime = "Saturday",
            imageUrl = "https://beertime.bg/wp-content/uploads/2022/10/hazyipa.webp",
            type = EventType.PARTY
        )
    )

    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        EventsCarousel(
            events = mockEvents,
            onEventClick = {}
        )
    }
}
