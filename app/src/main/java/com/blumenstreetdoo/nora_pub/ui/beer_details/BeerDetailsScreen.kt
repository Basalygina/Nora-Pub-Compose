package com.blumenstreetdoo.nora_pub.ui.beer_details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.ui.common.image.BeerImageScreenPerc
import com.blumenstreetdoo.nora_pub.ui.common.BeerInfoSection
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors

@Composable
fun BeerDetailsScreen(
    beerDetails: BeerDetails,
    onToggleFavorite: () -> Unit,
    onShareClick: (BeerDetails) -> Unit,
    onNoteChange: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DetailsTopAppBar(
                onBackClick = onBackClick,
                onShareClick = { onShareClick(beerDetails) },
                onToggleFavorite = onToggleFavorite,
                isFavorite = beerDetails.isFavorite
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BeerImageScreenPerc(
                imageUrl = beerDetails.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f / 0.8f),
                screenPercentage = 0.4f,
                cornerRadius = 0,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            //Beer Info
            BeerInfoSection(beerDetails, modifier = Modifier)

            //NoteTextField
            if (beerDetails.isFavorite) {
                var note by rememberSaveable { mutableStateOf(beerDetails.note ?: "") }

                NoteTextField(
                    note = note,
                    onNoteChange = { newNote ->
                        note = newNote
                        onNoteChange(newNote)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Beer description
            Text(
                text = beerDetails.description ?: "",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsTopAppBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    isFavorite: Boolean
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = NoraColors.onSurface,
            titleContentColor = NoraColors.surface
        ),
        title = { Text(stringResource(R.string.details)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = stringResource(R.string.share),
                    tint = Color.White
                )
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.ic_favorites_active_red
                        else R.drawable.ic_favorites_not_active
                    ),
                    contentDescription = stringResource(R.string.title_profile),
                    tint = if (isFavorite) Color.Unspecified else Color.White
                )
            }
        }
    )
}

@Composable
fun NoteTextField(
    note: String,
    onNoteChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = note,
            onValueChange = onNoteChange,
            label = {
                Text(
                    text = if (note.isEmpty()) {
                        stringResource(id = R.string.add_note_hint)
                    } else {
                        stringResource(id = R.string.edit_note_hint)
                    }
                )
            },
            trailingIcon = {

                if (isFocused && note.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onNoteChange(note)
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.done),
                            tint = Color.Gray
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF6DFA7), Color(0xFFFFFFFF))
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(bottom = 8.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Night mode")
@Composable
fun BeerDetailsScreenPreview() {
    MaterialTheme {
        BeerDetailsScreen(
            beerDetails = BeerDetails(
                id = "1",
                name = "IIPPAA",
                description = "A refreshing IPA with citrus notes.",
                abv = 5.5,
                imageUrl = null,
                brewery = Brewery("1", "Brewery Name", "Country", "type", "loc"),
                beerIbu = 30,
                beerStyle = "IPA",
                isFavorite = true,
                note = "My favorite beer!"
            ),
            onToggleFavorite = {},
            onShareClick = {},
            onNoteChange = {},
            onBackClick = {}
        )
    }
}