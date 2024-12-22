package com.blumenstreetdoo.nora_pub.domain.models

import java.io.Serializable

class Beer(
    id: String,
    name: String,
    type: DrinkType,
    description: String? = null,
    price: Double,
    volume: Int,
    abv: Double,
    imageUrl: String? = null,
    val brewery: Brewery,
    val beerIbu: Int?, // e.g. 8.0
    val beerStyleId: String?, // e.g. "171"
    val beerStyle: String?, // e.g. "Berliner Weisse"
) : Serializable, Drink(id, name, type, description, price, volume, abv, imageUrl)