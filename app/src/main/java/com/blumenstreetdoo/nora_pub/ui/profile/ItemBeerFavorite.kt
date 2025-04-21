package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.common.BeerImageDp
import com.blumenstreetdoo.nora_pub.ui.common.BeerInfoSection

@Composable
fun ItemBeerFavorite(
    beerDetails: BeerDetails,
    favBeer: FavoriteBeer,
    modifier: Modifier,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onItemClick(beerDetails) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFD1D1D1))
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFF6DFA7))
                    )
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Beer Image
            BeerImageDp(
                imageUrl = beerDetails.imageUrl,
                modifier = Modifier,
                imageSize = 60,
                cornerRadius = 8,
                paddingStart = 12,
                paddingTop = 4,
            )

            // Beer Info
            BeerInfoSection(beerDetails = beerDetails, modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
            )

            // Favorite and Note Icons
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favorites_active_red),
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onIconFavoriteClick(favBeer) },
                )
                if (!beerDetails.note.isNullOrEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_note_active),
                        contentDescription = "Note",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemBeerFavoritePreview() {
    MaterialTheme {
        ItemBeerFavorite(
            beerDetails = BeerDetails(
                id = "1",
                name = "IIPPAA",
                brewery = Brewery(
                    "1", "Brewery", "Serbia", "1", "1"
                ),
                beerStyle = "Berliner Weisse",
                abv = 5.0,
                beerIbu = 28,
                note = "dfd",
                description = "Berliner Weisse",
                imageUrl = "",
                isFavorite = true
            ),
            favBeer = FavoriteBeer(
                id = "1",
                name = "IIPPAA",
                description = "Berliner Weisse",
                abv = 5.0,
                imageUrl = null,
                brewery = Brewery(
                    "1", "Brewery", "Serbia", "1", "1"
                ),
                beerIbu = 28,
                beerStyleId = "1",
                beerStyle = "Berliner Weisse",
                note = "dfd"
            ),
            modifier = Modifier,
            onItemClick = {},
            onIconFavoriteClick = {}
        )
    }
}