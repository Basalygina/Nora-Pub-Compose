package com.blumenstreetdoo.nora_pub.testutil

import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer

object BeerTestFactory {
    fun sampleFavoriteBeer(id: String) = FavoriteBeer(
        id = id,
        name = "Test beer name $id",
        description = "Test Favorite Description",
        abv = 6.6,
        imageUrl = "https://test.url/favorite.jpg",
        brewery = sampleBrewery(id),
        beerIbu = 40,
        beerStyleId = "style$id",
        beerStyle = "Test Lager",
        note = "Favorite Note"
    )

    fun sampleBeerCanned(id: String) = Beer(
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

    fun sampleBeerOnTap(id: String) = Beer(
        id = id,
        name = "Mocked Beer",
        type = DrinkType.TAP_BEER,
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

    private fun sampleBrewery(id: String): Brewery {
        return Brewery("123$id", "Test Brewery", "Test Country", "Test City", "http://brew.com")
    }
}