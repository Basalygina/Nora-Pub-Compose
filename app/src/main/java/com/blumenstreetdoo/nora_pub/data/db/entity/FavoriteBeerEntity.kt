package com.blumenstreetdoo.nora_pub.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blumenstreetdoo.nora_pub.domain.models.Brewery

@Entity(tableName = "favorite_beer_table")
data class FavoriteBeerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val abv: Double,
    val imageUrl: String?,
    val brewery: Brewery,
    val beerIbu: Int?,
    val beerStyleId: String?,
    val beerStyle: String?,
    val note: String?
)