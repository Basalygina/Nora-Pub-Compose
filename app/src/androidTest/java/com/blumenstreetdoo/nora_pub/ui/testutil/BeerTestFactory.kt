package com.blumenstreetdoo.nora_pub.testutil

import com.blumenstreetdoo.nora_pub.domain.models.Brewery
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

    private fun sampleBrewery(id: String): Brewery {
        return Brewery("123$id", "Test Brewery", "Test Country", "Test City", "http://brew.com")
    }
}