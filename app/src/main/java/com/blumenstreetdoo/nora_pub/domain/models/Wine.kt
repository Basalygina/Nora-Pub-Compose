package com.blumenstreetdoo.nora_pub.domain.models

class Wine(
    id: String,
    name: String,
    description: String,
    price: Double,
    volume: Int,
    abv: Double,
    imageUrl: String,
    val winery: String, // e.g. "L.O Wine"
    val grapeVariety: String, // e.g. "Merlot"
    val wineRegion: String, // e.g. "France, Bordeaux"
    val wineType: String // e.g. "Dry Red"
) : Drink(id, name, DrinkType.WINE, description, price, volume, abv, imageUrl)