package com.blumenstreetdoo.nora_pub.ui.event_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.ui.common.image.FullWidthBannerImage
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun EventDetailsScreen(event: Event) {
Column {
    FullWidthBannerImage(
        imageUrl = event.imageUrl,
        modifier = Modifier.fillMaxWidth()
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineSmall
        )

        if (!event.dateTime.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.dateTime,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (!event.description.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun EventDetailsScreenPreview() {
    val sampleEvent = Event(
        id = "1",
        title = "Craft Beer Tasting",
        dateTime = "Saturday, 7 PM",
        description = "Come join us for an evening of amazing craft beer tasting with local brewers.",
        imageUrl = "https://example.com/beer.jpg",
        type = EventType.PARTY
    )

    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        EventDetailsScreen(event = sampleEvent)
    }
}
