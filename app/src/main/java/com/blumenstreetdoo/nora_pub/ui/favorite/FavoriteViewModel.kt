package com.blumenstreetdoo.nora_pub.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.data.toFavoriteBeer
import com.blumenstreetdoo.nora_pub.domain.favorite.FavoriteBeerRepository
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteBeerRepository
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    val favoritesScreenState: LiveData<FavoriteScreenState> = repository.getAllFavoriteBeers()
        .map { favorites ->
            if (favorites.isEmpty())
                FavoriteScreenState.Empty
            else
                FavoriteScreenState.Content(favorites)
        }
        .asLiveData()

    fun checkFavorite(beerId: String) {
        viewModelScope.launch {
            _isFavorite.postValue(repository.isBeerFavorite(beerId))
            Log.d("DTest", "Favorite state checked: ${isFavorite.value}")
        }
    }

    fun toggleFavorite(beer: Beer) {
        viewModelScope.launch {
            if (repository.isBeerFavorite(beer.id)) {
                repository.deleteFavoriteBeerById(beer.id)
                _isFavorite.postValue(false)
            } else {
                repository.addFavoriteBeer(beer.toFavoriteBeer())
                _isFavorite.postValue(true)
            }
        }
    }
}