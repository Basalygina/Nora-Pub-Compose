package com.blumenstreetdoo.nora_pub.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.data.toFavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.favorite.FavoriteBeerRepository
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteBeerRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite.asStateFlow()

    val favoritesScreenState: StateFlow<FavoriteScreenState> = repository.getAllFavoriteBeers()
        .map { favorites ->
            when {
                favorites.isEmpty() -> FavoriteScreenState.Empty
                else -> FavoriteScreenState.Content(favorites)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavoriteScreenState.Loading
        )

    fun checkFavorite(beerId: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isBeerFavorite(beerId)
        }
    }

    fun toggleFavorite(beer: Beer) {
        viewModelScope.launch {
            if (repository.isBeerFavorite(beer.id)) {
                repository.deleteFavoriteBeerById(beer.id)
                _isFavorite.value = false
            } else {
                repository.addFavoriteBeer(beer.toFavoriteBeer())
                _isFavorite.value = true
            }
        }
    }
}