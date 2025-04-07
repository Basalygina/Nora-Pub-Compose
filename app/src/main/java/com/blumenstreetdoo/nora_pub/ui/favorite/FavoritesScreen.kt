package com.blumenstreetdoo.nora_pub.ui.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.models.toDetails
import com.blumenstreetdoo.nora_pub.ui.common.ErrorState
import com.blumenstreetdoo.nora_pub.ui.common.LoadingState
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography


@Composable
fun FavoritesScreen(
    state: FavoriteScreenState,
    onItemClick: (BeerDetails) -> Unit,
    onIconFavoriteClick: (FavoriteBeer) -> Unit
) {
    when (state) {
        is FavoriteScreenState.Loading -> LoadingState()
        is FavoriteScreenState.Content -> ContentState(state.favorites, onItemClick, onIconFavoriteClick)
        is FavoriteScreenState.Error -> ErrorState(text = state.message)
        is FavoriteScreenState.Empty -> ErrorState(text = stringResource(R.string.no_favorites_message))
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

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MaterialTheme(
        colorScheme = NoraColors,
        typography = NoraTypography,
    )  {
        FavoritesScreen(FavoriteScreenState.Loading, {}, {})
    }
}