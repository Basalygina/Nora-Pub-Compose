package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.domain.models.toDetails
import com.blumenstreetdoo.nora_pub.ui.common.ErrorState
import com.blumenstreetdoo.nora_pub.ui.common.LoadingState

@Composable
fun BeerListScreen(
    drinkType: DrinkType,
    craftState: CraftScreenState,
    onBeerClick: (BeerDetails) -> Unit
) {
    when (craftState) {
        is CraftScreenState.Loading -> LoadingState()
        is CraftScreenState.Error -> ErrorState(text = craftState.message)
        is CraftScreenState.Content -> {
            val fullBeerList = craftState.fullBeerList
            val filteredList = fullBeerList.filter { it.type == drinkType }
            if (filteredList.isEmpty()) {
                ErrorState(text = stringResource(R.string.no_items_found))
            } else {
                BeerListContent(filteredList, onBeerClick)
            }
        }
    }
}

@Composable
fun BeerListContent(
    beers: List<Beer>,
    onBeerClick: (BeerDetails) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        items(beers, key = { it.id }) { beer ->
            ItemBeer(
                beer = beer,
                beerDetails = beer.toDetails(),
                modifier = Modifier,
                onItemClick = onBeerClick
            )
        }
    }
}