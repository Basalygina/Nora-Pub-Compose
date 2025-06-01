package com.blumenstreetdoo.nora_pub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.NewsType
import com.blumenstreetdoo.nora_pub.ui.common.ErrorState
import com.blumenstreetdoo.nora_pub.ui.common.LoadingState
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onEventClick: (Event) -> Unit,
    onNewsClick: (News) -> Unit,
    onMenuClick: () -> Unit,
    onUntappdClick: () -> Unit,
    onPhoneClick: (String) -> Unit,
    onDirectionsClick: () -> Unit,
    onInstagramClick: () -> Unit,
    onBookATableClick: () -> Unit,
    onSeeAllNewsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NoraColors.background)
            .padding(bottom = 80.dp),

        ) {
        when (state) {
            is HomeScreenState.Content -> {
                HomeContent(
                    events = state.events,
                    news = state.news,
                    onEventClick = onEventClick,
                    onNewsClick = onNewsClick,
                    onMenuClick = onMenuClick,
                    onUntappdClick = onUntappdClick,
                    onPhoneClick = onPhoneClick,
                    onDirectionsClick = onDirectionsClick,
                    onInstagramClick = onInstagramClick,
                    onSeeAllNewsClick = onSeeAllNewsClick,
                )
            }

            HomeScreenState.Loading -> LoadingState()

            is HomeScreenState.Error -> ErrorState(text = state.message)

            HomeScreenState.NoInternet -> ErrorState(
                text = stringResource(R.string.error_message_no_internet),
                isPositive = true
            )
        }

        FloatingActionButton(
            onClick = onBookATableClick,
            shape = RoundedCornerShape(24.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.book_a_table),
                style = NoraTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        HomeScreen(
            state = HomeScreenState.Content(
                events = listOf(
                    Event(
                        id = "1",
                        title = "DJ Night",
                        description = "Live music and party",
                        dateTime = "Friday 21:00",
                        imageUrl = "",
                        type = EventType.PARTY
                    )
                ),
                news = listOf(
                    News(
                        id = "1",
                        title = "New Craft Beer!",
                        description = "Try our new IPA this weekend.",
                        imageUrl = "",
                        type = NewsType.NEW_ARRIVAL
                    )
                )
            ),
            onEventClick = {},
            onNewsClick = {},
            onMenuClick = {},
            onUntappdClick = {},
            onPhoneClick = {},
            onDirectionsClick = {},
            onInstagramClick = {},
            onBookATableClick = {},
            onSeeAllNewsClick = {}
        )
    }
}
