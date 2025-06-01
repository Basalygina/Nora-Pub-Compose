package com.blumenstreetdoo.nora_pub.ui.home.components.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.EventType
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun EventCard(event: Event, modifier: Modifier = Modifier) {

    val hasImage = !event.imageUrl.isNullOrBlank()
    val labelBackground = if (hasImage) NoraColors.background else NoraColors.onPrimary
    val labelTextColor = if (hasImage) NoraColors.onBackground else NoraColors.background

    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageUrl)
                    .placeholder(R.drawable.placeholder_nora)
                    .error(R.drawable.placeholder_nora)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall.copy(color = labelTextColor),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(labelBackground, RoundedCornerShape(4.dp))
                        .padding(vertical = 2.dp, horizontal = 4.dp)
                )
                Text(
                    text = event.dateTime ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(color = labelTextColor),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(labelBackground, RoundedCornerShape(4.dp))
                        .padding(vertical = 2.dp, horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventItemPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography,
    ) {
        EventCard(
            event = Event(
                id = "1",
                title = "Party Night",
                description = "Join us for an amazing party night with live music!",
                dateTime = "Every friday",
                imageUrl = null,
                type = EventType.PARTY
            ),
        )
    }
}