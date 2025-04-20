package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R

@Composable
fun NotificationSettings(
    subscribeNews: Boolean,
    subscribeEvents: Boolean,
    onCheckedChange: (Boolean, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    showSaveButton: Boolean = true,
    onSave: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.notifications_hint),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = subscribeNews,
                onCheckedChange = { checked ->
                    onCheckedChange(checked, subscribeEvents)
                }
            )
            Text(text = stringResource(R.string.latest_news))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = subscribeEvents,
                onCheckedChange = { checked ->
                    onCheckedChange(subscribeNews, checked)
                }
            )
            Text(text = stringResource(R.string.events))
        }
        if (showSaveButton) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onSave() },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}