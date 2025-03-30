package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.domain.models.toDetails

@Composable
fun BeerListScreen(
    drinkType: DrinkType,
    craftState: CraftScreenState,
    onBeerClick: (BeerDetails) -> Unit
) {
    when (craftState) {
        is CraftScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CraftScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = craftState.message)
            }
        }
        is CraftScreenState.Content -> {
            val fullBeerList = craftState.fullBeerList
            val filteredList = fullBeerList.filter { it.type == drinkType }
            BeerListContent(filteredList, onBeerClick)
        }
    }
}

@Composable
fun BeerListContent(
    beers: List<Beer>,
    onBeerClick: (BeerDetails) -> Unit
) {
    LazyColumn {
        items(beers) { beer ->
            ItemBeer(
                beer = beer,
                beerDetails = beer.toDetails(),
                modifier = Modifier,
                onItemClick = onBeerClick
            )
        }
    }
}