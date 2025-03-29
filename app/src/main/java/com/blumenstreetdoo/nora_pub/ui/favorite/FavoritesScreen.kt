package com.blumenstreetdoo.nora_pub.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.models.toDetails


@Composable
fun FavoritesScreen(
    state: FavoriteScreenState,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit
) {
    when (state) {
        is FavoriteScreenState.Loading -> LoadingState()
        is FavoriteScreenState.Content -> ContentState(state.favorites, onItemClick, onIconFavoriteClick)
        is FavoriteScreenState.Error -> ErrorState(state.message)
        is FavoriteScreenState.Empty -> EmptyState()
    }

}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error_black),
            contentDescription = "Empty"
        )
        Text(
            text = stringResource(id = R.string.no_favorites_message),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun ErrorState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error_black),
            contentDescription = "Error"
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun ContentState(
    favorites: List<FavoriteBeer>,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit
) {
    LazyColumn {
        items(favorites) { favBeer ->
            ItemBeerFavorite(
                beerDetails = favBeer.toDetails(),
                favBeer = favBeer,
                modifier = Modifier,
                onItemClick = onItemClick,
                onIconFavoriteClick = onIconFavoriteClick
            )
        }
    }
}

@Composable
fun LoadingState() {
    CircularProgressIndicator(
        color = Color(0xFFFAAB1A),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MaterialTheme {
        FavoritesScreen(FavoriteScreenState.Loading, {}, {})
    }
}