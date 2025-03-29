package com.blumenstreetdoo.nora_pub.domain.models

data class BeerDetails(
    val id: String,
    val name: String,
    val description: String?,
    val abv: Double,
    val imageUrl: String?,
    val brewery: Brewery,
    val beerIbu: Int?,
    val beerStyle: String?,
    val isFavorite: Boolean,
    val note: String? = null
)

fun Beer.toDetails(isFavorite: Boolean = false): BeerDetails {
    return BeerDetails(
        id = id,
        name = name,
        description = description,
        abv = abv,
        imageUrl = imageUrl,
        brewery = brewery,
        beerIbu = beerIbu,
        beerStyle = beerStyle,
        isFavorite = isFavorite,
        note = null
    )
}

fun FavoriteBeer.toDetails(): BeerDetails {
    return BeerDetails(
        id = id,
        name = name,
        description = description,
        abv = abv,
        imageUrl = imageUrl,
        brewery = brewery,
        beerIbu = beerIbu,
        beerStyle = beerStyle,
        isFavorite = true,
        note = note
    )
}