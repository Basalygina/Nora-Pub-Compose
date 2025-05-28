package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.ui.common.BeerImageDp
import com.blumenstreetdoo.nora_pub.ui.common.BeerInfoSection
import com.blumenstreetdoo.nora_pub.ui.common.TestTags

@Composable
fun ItemBeer(
    beer: Beer,
    beerDetails: BeerDetails,
    modifier: Modifier,
    onItemClick: (BeerDetails) -> Unit,
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .testTag("${TestTags.BEER_ITEM_PREFIX}${beer.id}")
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

            // Price and volume
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${beer.price}€",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${beer.volume} ml",
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemBeerPreview() {
    MaterialTheme {
        ItemBeer(
            beer = Beer(
                id = "1",
                name = "IIPPAA",
                brewery = Brewery(
                    "1", "Brewery", "Serbia", "1", "1"
                ),
                beerStyle = "Berliner Weisse",
                abv = 5.0,
                beerIbu = 28,
                description = "Berliner Weisse",
                imageUrl = "",
                type = DrinkType.CANNED_BEER,
                price = 12.0,
                volume = 500,
                beerStyleId = "3"
            ),
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
            modifier = Modifier,
            onItemClick = {},
        )
    }
}