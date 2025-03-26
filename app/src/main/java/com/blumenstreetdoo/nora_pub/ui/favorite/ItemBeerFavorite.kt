package com.blumenstreetdoo.nora_pub.ui.favorite

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

@Composable
fun ItemBeerFavorite(
    favBeer: FavoriteBeer,
    modifier: Modifier,
    onItemClick: (FavoriteBeer) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(favBeer) },
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
            BeerImage(
                imageUrl = favBeer.imageUrl,
                placeholderRes = R.drawable.placeholder_nora_large,
                modifier = Modifier
            )

            // Beer Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                // Name
                Text(
                    text = favBeer.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Brewery
                Text(
                    text = "by ${favBeer.brewery.name}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Style
                Text(
                    text = favBeer.beerStyle ?: "",
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // ABV and IBU
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${favBeer.abv}% ABV",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        color = Color.Black
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_ellipse),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(4.dp)
                    )
                    Text(
                        text = "${favBeer.beerIbu} IBU",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        color = Color.Black
                    )
                }
            }
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
                    modifier = Modifier.size(24.dp)
                    .clickable { onIconFavoriteClick(favBeer) },
                )
                if (!favBeer.note.isNullOrEmpty()) {
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
            favBeer = FavoriteBeer(
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
                beerStyleId = "1"
            ),
            modifier = Modifier,
            onItemClick = {},
            onIconFavoriteClick = {}
        )
    }
}