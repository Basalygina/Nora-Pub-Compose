package com.blumenstreetdoo.nora_pub.data

import com.blumenstreetdoo.nora_pub.domain.api.MenuRepository
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MenuRepositoryImpl : MenuRepository {
    override fun getCraftList(): Flow<Resource<List<Beer>>> = flow {
        emit(Resource.Success(generateMockCraftList())) // add ?: emptyList()
    }

    override fun getBeerById(id: String): Resource<Beer> {
        val event = generateMockCraftList().find { it.id == id }
        return event?.let { Resource.Success(it) } ?: Resource.Error("Event not found")
    }

    private fun generateMockCraftList(): List<Beer> = listOf(
        Beer(
            id = "1",
            name = "IIPPAA",
            type = DrinkType.TAP_BEER,
            description = "IIPPAA is the best!",
            price = 6.0,
            volume = 500,
            abv = 8.1,
            imageUrl = "example",
            brewery = Brewery(
                id = "1",
                name = "TOP beer",
                country = "Serbia",
                city = "Beograd",
                url = "example"
            ),
            beerIbu = 60,
            beerStyleId = "2",
            beerStyle = "IPA"
        ),
        Beer(
            id = "2",
            name = "2 IIPPAA loashj asfjh ;sljdh ;dlfhg ;sdhk",
            type = DrinkType.TAP_BEER,
            description = "2 IIPPAA is the best!",
            price = 6.0,
            volume = 500,
            abv = 8.1,
            imageUrl = "example",
            brewery = Brewery(
                id = "1",
                name = "TOP beer",
                country = "Serbia",
                city = "Beograd",
                url = "example"
            ),
            beerIbu = 60,
            beerStyleId = "2",
            beerStyle = "Berliner Weisse"
        ),
        Beer(
            id = "3",
            name = "3 IIPPAA",
            type = DrinkType.CANNED_BEER,
            description = "3 IIPPAA is the best!",
            price = 6.5,
            volume = 500,
            abv = 8.1,
            imageUrl = "example",
            brewery = Brewery(
                id = "1",
                name = "TOP beer",
                country = "Serbia",
                city = "Beograd",
                url = "example"
            ),
            beerIbu = 60,
            beerStyleId = "2",
            beerStyle = "IPA"
        ),
    )
}