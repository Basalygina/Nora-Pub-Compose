package com.blumenstreetdoo.nora_pub.domain.models

class Beer(
    id: String,
    name: String,
    type: DrinkType,
    description: String,
    price: Double,
    volume: Int,
    abv: Double,
    imageUrl: String,
    val brewery: Brewery,
    val beerIbu: Double, // e.g. 8.0
    val beerStyleId: String, // e.g. "171"
    val beerStyle: String, // e.g. "Berliner Weisse"
) : Drink(id, name, type, description, price, volume, abv, imageUrl)