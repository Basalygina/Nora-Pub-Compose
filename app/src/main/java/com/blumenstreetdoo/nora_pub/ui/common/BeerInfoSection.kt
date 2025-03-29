package com.blumenstreetdoo.nora_pub.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.Brewery

@Composable
fun BeerInfoSection(beerDetails: BeerDetails, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        // Name
        Text(
            text = beerDetails.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Brewery
        Text(
            text = "by ${beerDetails.brewery.name}",
            fontSize = 16.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Style
        if (!beerDetails.beerStyle.isNullOrEmpty()) {
            Text(
                text = beerDetails.beerStyle,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // ABV and IBU
        BeerAbvIbuInfo(abv = beerDetails.abv, ibu = beerDetails.beerIbu)
    }
}


@Composable
fun BeerAbvIbuInfo(abv: Double, ibu: Int?) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$abv% ABV",
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
        ibu?.let {
            Text(
                text = "$it IBU",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BeerInfoSectionPreview() {
    val sampleBeer = BeerDetails(
        id = "1",
        name = "Sample IPA",
        description = "A refreshing IPA with citrus notes.",
        abv = 6.5,
        imageUrl = null,
        brewery = Brewery("1", "Brewery Name", "Country", "type", "loc"),
        beerIbu = 40,
        beerStyle = "IPA",
        note = null,
        isFavorite = true,
    )

    MaterialTheme {
        BeerInfoSection(sampleBeer)
    }
}

