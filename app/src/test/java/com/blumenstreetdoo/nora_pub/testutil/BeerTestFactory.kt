package com.blumenstreetdoo.nora_pub.testutil

import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType

object BeerTestFactory {
    fun sampleBeer(id: String) = Beer(
        id = id,
        name = "Mocked Beer",
        type = DrinkType.CANNED_BEER,
        description = "Test Description",
        price = 5.0,
        volume = 500,
        abv = 5.0,
        imageUrl = "https://test.url/beer.jpg",
        brewery = sampleBrewery(id),
        beerIbu = 20,
        beerStyleId = "456$id",
        beerStyle = "IPA Test"
    )

    fun sampleBeerEntity(id: String) = FavoriteBeerEntity(
        id = id,
        name = "Mocked Beer Entity",
        description = "Test Entity Description",
        abv = 7.1,
        imageUrl = "https://test.url/beer.jpg",
        brewery = sampleBrewery(id),
        beerIbu = 60,
        beerStyleId = "456$id",
        beerStyle = "IPA Test",
        note = "Best Test"
    )

    private fun sampleBrewery(id: String): Brewery {
        return Brewery("123$id", "Test Brewery", "Test Country", "Test City", "http://brew.com")
    }
}