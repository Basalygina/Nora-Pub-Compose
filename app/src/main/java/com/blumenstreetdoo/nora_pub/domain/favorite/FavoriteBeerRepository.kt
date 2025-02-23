package com.blumenstreetdoo.nora_pub.domain.favorite

import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import kotlinx.coroutines.flow.Flow

interface FavoriteBeerRepository {
    fun getAllFavoriteBeers(): Flow<List<FavoriteBeer>>
    fun getFavoriteBeerById(id: String): Flow<FavoriteBeer?>
    suspend fun addFavoriteBeer(favoriteBeer: FavoriteBeer)
    suspend fun deleteFavoriteBeerById(id: String)
    suspend fun isBeerFavorite(id: String): Boolean
    suspend fun updateFavoriteNote(beerId: String, note: String)

}