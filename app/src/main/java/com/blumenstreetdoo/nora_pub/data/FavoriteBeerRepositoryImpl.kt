package com.blumenstreetdoo.nora_pub.data

import com.blumenstreetdoo.nora_pub.data.db.dao.FavoriteBeerDao
import com.blumenstreetdoo.nora_pub.domain.favorite.FavoriteBeerRepository
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteBeerRepositoryImpl(
    private val dao: FavoriteBeerDao
) : FavoriteBeerRepository {

    override fun getAllFavoriteBeers(): Flow<List<FavoriteBeer>> {
        return dao.getAllFavoriteBeers().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getFavoriteBeerById(id: String): Flow<FavoriteBeer?> {
        return dao.getFavoriteBeerById(id).map { it?.toDomain() }
    }

    override suspend fun addFavoriteBeer(favoriteBeer: FavoriteBeer) {
        dao.addFavoriteBeer(favoriteBeer.toEntity())
    }

    override suspend fun deleteFavoriteBeerById(id: String) {
        dao.deleteFavoriteBeerById(id)
    }

    override suspend fun isBeerFavorite(id: String): Boolean {
        return dao.isBeerFavorite(id)
    }
}