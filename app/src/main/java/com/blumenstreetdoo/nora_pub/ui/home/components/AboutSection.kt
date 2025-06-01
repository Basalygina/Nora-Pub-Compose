package com.blumenstreetdoo.nora_pub.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun AboutSection(
    onPhoneClick: (String) -> Unit,
    onDirectionsClick: () -> Unit,
    onInstagramClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        // Top block with horizontal gradient background (yellow → white)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            NoraColors.secondary,
                            Color.White
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.doo_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.open_hours),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                LanguageSwitcher(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(start = 8.dp)
                )
            }
        }

        // Contact info block
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            val phoneText = stringResource(R.string.phone)
            ClickableUnderlinedText(
                text = phoneText,
                onClick = { onPhoneClick(phoneText) }
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.city),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.address),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            ClickableUnderlinedText(
                text = stringResource(R.string.get_directions),
                onClick = onDirectionsClick
            )

            Spacer(Modifier.height(4.dp))

            ClickableUnderlinedText(
                text = stringResource(R.string.instagram),
                onClick = onInstagramClick
            )
        }
    }
}

@Composable
private fun ClickableUnderlinedText(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Color.Black,
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
fun AboutSectionPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography
    ) {
        AboutSection(
            onPhoneClick = {},
            onDirectionsClick = {},
            onInstagramClick = {}
        )
    }
}