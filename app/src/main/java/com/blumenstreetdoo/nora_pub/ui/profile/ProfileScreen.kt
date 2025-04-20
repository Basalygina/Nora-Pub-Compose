package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    favoriteViewModel: FavoriteViewModel,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit,
    modifier: Modifier = Modifier
) {
    val profileState by profileViewModel.state.collectAsState()
    val favoriteState by favoriteViewModel.favoritesScreenState.collectAsState()

    val displayName = profileState.username.ifEmpty {
        stringResource(R.string.newbie)
    }

    var showNotificationDialog by rememberSaveable { mutableStateOf(false) }
    var showPhotoOptions by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileHeader(
            userName = displayName,
            photoPath = profileState.photoPath,
            onSaveName = profileViewModel::saveUsername,
            onPhotoClick = { showPhotoOptions = true },
            onOpenNotifications = { showNotificationDialog = true }
        )

        // Notification settings (only on first launch)

        if (profileState.isFirstLaunch) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                NotificationSettings(
                    subscribeNews = profileState.subscribeNews,
                    subscribeEvents = profileState.subscribeEvents,
                    onCheckedChange = profileViewModel::saveNotificationSettings,
                    modifier = Modifier.padding(16.dp),
                    showSaveButton = true,
                    onSave = profileViewModel::markFirstLaunchCompleted,
                )
            }
        }

        FavoritesScreen(
            state = favoriteState,
            onItemClick = onItemClick,
            onIconFavoriteClick = onIconFavoriteClick
        )
    }

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            text = {
                NotificationSettings(
                    subscribeNews = profileState.subscribeNews,
                    subscribeEvents = profileState.subscribeEvents,
                    onCheckedChange = profileViewModel::saveNotificationSettings,
                    showSaveButton = false,
                    onSave = profileViewModel::markFirstLaunchCompleted,
                )
            },
            confirmButton = { },
            dismissButton = {
                TextButton(
                    onClick = { showNotificationDialog = false },
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        ),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(stringResource(R.string.close))
                }
            }
        )
    }
}
