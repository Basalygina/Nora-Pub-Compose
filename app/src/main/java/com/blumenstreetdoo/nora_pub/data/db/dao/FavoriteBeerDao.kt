package com.blumenstreetdoo.nora_pub.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBeerDao {
    @Insert(entity = FavoriteBeerEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteBeer(beer: FavoriteBeerEntity)

    @Query("DELETE FROM favorite_beer_table WHERE id = :id")
    suspend fun deleteFavoriteBeerById(id: String)

    @Query("SELECT * FROM favorite_beer_table")
    fun getAllFavoriteBeers(): Flow<List<FavoriteBeerEntity>>

    @Query("SELECT * FROM favorite_beer_table where id = :id")
    fun getFavoriteBeerById(id: String): Flow<FavoriteBeerEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_beer_table WHERE id = :id)")
    suspend fun isBeerFavorite(id: String): Boolean

}