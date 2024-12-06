package com.blumenstreetdoo.nora_pub.domain.models

class Cocktail(
    id: String,
    name: String,
    description: String,
    price: Double,
    volume: Int,
    abv: Double,
    imageUrl: String,
    val ingredients: List<String>,
    val cocktailType: CocktailType
) : Drink(id, name, DrinkType.COCKTAIL, description, price, volume, abv, imageUrl)

enum class CocktailType {
    CLASSIC, SIGNATURE, SEASONAL
}