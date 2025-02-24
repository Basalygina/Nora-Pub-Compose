package com.blumenstreetdoo.nora_pub.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteBeer(
    val id: String,
    val name: String,
    val description: String? = null,
    val abv: Double,
    val imageUrl: String? = null,
    val brewery: Brewery,
    val beerIbu: Int?,
    val beerStyleId: String?,
    val beerStyle: String?,
    val note: String? = null
): Parcelable