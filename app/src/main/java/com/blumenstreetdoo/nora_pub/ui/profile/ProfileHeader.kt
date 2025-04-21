package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.foundation.border
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
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    userName: String? = null,
    photoPath: String?,
    onSaveName: (String) -> Unit,
    onPhotoClick: () -> Unit,
    onOpenNotifications: () -> Unit
) {
    var isEditingName by remember { mutableStateOf(false) }
    var editedName by remember(userName) { mutableStateOf(userName ?: "") }

    Card(
        modifier = modifier.fillMaxWidth(),
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
            // Profile picture container
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { onPhotoClick() }, // open gallery/camera dialog
                contentAlignment = Alignment.Center
            ) {
                if (!photoPath.isNullOrEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoPath)
                            .placeholder(R.drawable.ic_profile_placeholder)
                            .error(R.drawable.ic_profile_placeholder)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile_placeholder),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Name and EditName icon
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isEditingName) {
                        // Inline editing of user name
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
                        // Display user name
                        Text(
                            text = userName ?: stringResource(R.string.newbie),
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Toggle edit/done icon
                    IconButton(
                        onClick = {
                            if (isEditingName) onSaveName(editedName)
                            isEditingName = !isEditingName
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
                onClick = onOpenNotifications,
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
@Preview(showBackground = true)
fun HeaderPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography,
    ) {
        ProfileHeader(
            modifier = Modifier,
            userName = "User123",
            photoPath = "",
            onSaveName = {},
            onPhotoClick = {},
            onOpenNotifications = {},
        )
    }
}
