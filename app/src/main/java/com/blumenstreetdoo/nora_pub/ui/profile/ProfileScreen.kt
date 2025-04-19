package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    state: FavoriteScreenState,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit,
    modifier: Modifier = Modifier
) {
    val isFirstLaunch by profileViewModel.isFirstLaunchFlow.collectAsState(initial = true)
    val subscribeNews by profileViewModel.subscribeNewsFlow.collectAsState(initial = false)
    val subscribeEvents by profileViewModel.subscribeEventsFlow.collectAsState(initial = false)
    val username by profileViewModel.usernameFlow.collectAsState(initial = "")
    val displayName = username.ifEmpty { stringResource(R.string.newbie) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileHeader(
            profileViewModel = profileViewModel,
            userName = displayName,
            subscribeNews = subscribeNews,
            subscribeEvents = subscribeEvents
        )

        // Notification settings (only on first launch)

        if (isFirstLaunch) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                NotificationSettings(
                    subscribeNews = subscribeNews,
                    subscribeEvents = subscribeEvents,
                    onCheckedChange = { newsChecked, eventsChecked ->
                        profileViewModel.saveNotificationSettings(newsChecked, eventsChecked)
                    },
                    modifier = Modifier.padding(16.dp),
                    showSaveButton = true
                )
            }
        }

        FavoritesScreen(state, onItemClick, onIconFavoriteClick)
    }
}

@Composable
fun ProfileHeader(
    profileViewModel: ProfileViewModel,
    userName: String? = null,
    subscribeNews: Boolean,
    subscribeEvents: Boolean
) {
    var isEditingName by remember { mutableStateOf(false) }
    var editedName by remember(userName) { mutableStateOf(userName ?: "") }
    var showNotificationDialog by remember { mutableStateOf(false) }

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            text = {
                NotificationSettings(
                    subscribeNews = subscribeNews,
                    subscribeEvents = subscribeEvents,
                    onCheckedChange = { news, events ->
                        profileViewModel.saveNotificationSettings(news, events)
                    },
                    showSaveButton = false
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showNotificationDialog = false
                    }
                ) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showNotificationDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = NoraColors.onBackground,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text("Close")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //Profile picture
            Icon(
                imageVector = Icons.Default.AccountCircle,
                tint = NoraColors.secondary,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .border(
                        width = 2.dp,
                        color = NoraColors.primary,
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Name and EditName icon
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isEditingName) {
                        BasicTextField(
                            value = editedName,
                            onValueChange = { editedName = it },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    val y = size.height - strokeWidth / 2 - 6.dp.toPx()
                                    drawLine(
                                        color = NoraColors.primary,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                                .padding(vertical = 0.dp),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.headlineMedium,
                        ) { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                            }
                        }
                    } else {
                        Text(
                            text = userName ?: stringResource(R.string.newbie),
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(
                        onClick = {
                            if (isEditingName) {
                                profileViewModel.saveUsername(editedName)
                                isEditingName = false
                            } else {
                                isEditingName = true
                            }
                        },
                        modifier = Modifier.padding(top = 2.dp, end = 2.dp, bottom = 2.dp)
                    ) {
                        Icon(
                            imageVector = if (isEditingName) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditingName) "Done" else stringResource(R.string.edit_name),
                            tint = NoraColors.onPrimary
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.craft_beer_enthusiast),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.offset(y = (-8).dp)
                )
            }

            // Notification settings icon
            IconButton(
                onClick = { showNotificationDialog = true },
                modifier = Modifier.offset(x = 8.dp, y = (-10).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.notification_settings),
                    tint = NoraColors.onPrimary
                )
            }
        }
    }
}


@Composable
fun NotificationSettings(
    subscribeNews: Boolean,
    subscribeEvents: Boolean,
    onCheckedChange: (Boolean, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    showSaveButton: Boolean = true
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
                    onClick = { onCheckedChange(subscribeNews, subscribeEvents) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}