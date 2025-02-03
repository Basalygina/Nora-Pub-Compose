package com.blumenstreetdoo.nora_pub.ui.favorite

import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

sealed class FavoriteScreenState {
    data object Loading : FavoriteScreenState()
    data class Content(val favorites: List<FavoriteBeer>) : FavoriteScreenState()
    data object Empty : FavoriteScreenState()
    data class Error(val message: String) : FavoriteScreenState()
}