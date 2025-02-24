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
            name = "Pliny the Elder",
            type = DrinkType.CANNED_BEER,
            description = "One of the most sought-after Double IPAs, packed with bold hop aroma and bitterness.",
            price = 8.99,
            volume = 473,
            abv = 8.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/22511.jpg",
            brewery = Brewery(
                id = "1",
                name = "Russian River Brewing Company",
                country = "USA",
                city = "Santa Rosa, CA",
                url = "https://russianriverbrewing.com/"
            ),
            beerIbu = 100,
            beerStyleId = "1",
            beerStyle = "Double IPA"
        ),
        Beer(
            id = "2",
            name = "Heady Topper",
            type = DrinkType.CANNED_BEER,
            description = "A world-class New England IPA known for its hazy appearance and intense tropical fruit flavors.",
            price = 9.50,
            volume = 473,
            abv = 8.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/6396.jpg",
            brewery = Brewery(
                id = "2",
                name = "The Alchemist",
                country = "USA",
                city = "Stowe, VT",
                url = "https://alchemistbeer.com/"
            ),
            beerIbu = 75,
            beerStyleId = "2",
            beerStyle = "New England IPA"
        ),
        Beer(
            id = "3",
            name = "Westvleteren 12",
            type = DrinkType.CANNED_BEER,
            description = "One of the most revered Belgian Quadrupels, brewed by Trappist monks in limited quantities.",
            price = 12.99,
            volume = 330,
            abv = 10.2,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/18307.jpg",
            brewery = Brewery(
                id = "3",
                name = "Westvleteren Brewery",
                country = "Belgium",
                city = "Westvleteren",
                url = "https://sintsixtus.be/"
            ),
            beerIbu = 35,
            beerStyleId = "3",
            beerStyle = "Belgian Quadrupel"
        ),
        Beer(
            id = "4",
            name = "Speedway Stout",
            type = DrinkType.CANNED_BEER,
            description = "A robust and smooth Imperial Stout brewed with coffee beans, delivering deep roasty flavors.",
            price = 10.99,
            volume = 473,
            abv = 12.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/6938.jpg",
            brewery = Brewery(
                id = "4",
                name = "AleSmith Brewing Company",
                country = "USA",
                city = "San Diego, CA",
                url = "https://alesmith.com/"
            ),
            beerIbu = 70,
            beerStyleId = "4",
            beerStyle = "Imperial Stout"
        ),
        Beer(
            id = "5",
            name = "Bourbon County Stout",
            type = DrinkType.CANNED_BEER,
            description = "Aged in bourbon barrels, this stout is rich with notes of chocolate, vanilla, and caramel.",
            price = 14.99,
            volume = 500,
            abv = 14.5,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/1568.jpg",
            brewery = Brewery(
                id = "5",
                name = "Goose Island Beer Co.",
                country = "USA",
                city = "Chicago, IL",
                url = "https://www.gooseisland.com/"
            ),
            beerIbu = 60,
            beerStyleId = "5",
            beerStyle = "Barrel-Aged Stout"
        ),
        Beer(
            id = "6",
            name = "Péché Mortel",
            type = DrinkType.CANNED_BEER,
            description = "An intensely rich coffee-infused Imperial Stout brewed with deep roasted malts.",
            price = 11.49,
            volume = 341,
            abv = 9.5,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/38118.jpg",
            brewery = Brewery(
                id = "6",
                name = "Brasserie Dieu du Ciel!",
                country = "Canada",
                city = "Montreal, QC",
                url = "https://dieuduciel.com/"
            ),
            beerIbu = 80,
            beerStyleId = "6",
            beerStyle = "Imperial Stout"
        ),
        Beer(
            id = "7",
            name = "Kentucky Breakfast Stout",
            type = DrinkType.CANNED_BEER,
            description = "A bourbon-barrel aged stout with chocolate and coffee flavors.",
            price = 13.99,
            volume = 355,
            abv = 12.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/420.jpg",
            brewery = Brewery(
                id = "7",
                name = "Founders Brewing Co.",
                country = "USA",
                city = "Grand Rapids, MI",
                url = "https://foundersbrewing.com/"
            ),
            beerIbu = 70,
            beerStyleId = "7",
            beerStyle = "Bourbon Barrel-Aged Stout"
        ),
        Beer(
            id = "8",
            name = "Hazy Little Thing",
            type = DrinkType.CANNED_BEER,
            description = "A juicy and unfiltered New England IPA with bright citrus and tropical notes.",
            price = 6.99,
            volume = 355,
            abv = 6.7,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/480123.jpg",
            brewery = Brewery(
                id = "8",
                name = "Sierra Nevada Brewing Co.",
                country = "USA",
                city = "Chico, CA",
                url = "https://sierranevada.com/"
            ),
            beerIbu = 35,
            beerStyleId = "8",
            beerStyle = "New England IPA"
        ),
        Beer(
            id = "9",
            name = "Zombie Dust",
            type = DrinkType.CANNED_BEER,
            description = "A world-famous Pale Ale with intense citrus hop flavors.",
            price = 7.99,
            volume = 355,
            abv = 6.2,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/136185.jpg",
            brewery = Brewery(
                id = "9",
                name = "3 Floyds Brewing Co.",
                country = "USA",
                city = "Munster, IN",
                url = "https://www.3floyds.com/"
            ),
            beerIbu = 60,
            beerStyleId = "9",
            beerStyle = "Pale Ale"
        ),
        Beer(
            id = "10",
            name = "Two Hearted Ale",
            type = DrinkType.CANNED_BEER,
            description = "A balanced and hoppy American IPA, brewed with 100% Centennial hops.",
            price = 7.49,
            volume = 355,
            abv = 7.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/15048.jpg",
            brewery = Brewery("10", "Bell's Brewery", "USA", "Comstock, MI", "https://www.bellsbeer.com/"),
            beerIbu = 55,
            beerStyleId = "10",
            beerStyle = "American IPA"
        ),
        Beer(
            id = "31",
            name = "Weihenstephaner Hefeweissbier",
            type = DrinkType.TAP_BEER,
            description = "A classic Bavarian wheat beer with notes of banana, clove, and a smooth mouthfeel.",
            price = 5.99,
            volume = 500,
            abv = 5.4,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/738.jpg",
            brewery = Brewery("31", "Bayerische Staatsbrauerei Weihenstephan", "Germany", "Freising", "https://www.weihenstephaner.de/"),
            beerIbu = 14,
            beerStyleId = "31",
            beerStyle = "Hefeweizen"
        ),
        Beer(
            id = "32",
            name = "Paulaner Münchner Hell",
            type = DrinkType.TAP_BEER,
            description = "A smooth and refreshing Munich Helles Lager with a well-balanced malt character.",
            price = 5.49,
            volume = 500,
            abv = 4.9,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/29466.jpg",
            brewery = Brewery("32", "Paulaner Brauerei", "Germany", "Munich", "https://www.paulaner.com/"),
            beerIbu = 18,
            beerStyleId = "32",
            beerStyle = "Helles Lager"
        ),
        Beer(
            id = "33",
            name = "Augustiner Bräu Edelstoff",
            type = DrinkType.TAP_BEER,
            description = "A crisp, bright, and golden export lager with a smooth finish and a hint of sweetness.",
            price = 5.99,
            volume = 500,
            abv = 5.6,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/42919.jpg",
            brewery = Brewery("33", "Augustiner Bräu", "Germany", "Munich", "https://www.augustiner-braeu.de/"),
            beerIbu = 22,
            beerStyleId = "33",
            beerStyle = "Dortmunder Export"
        ),
        Beer(
            id = "34",
            name = "Pilsner Urquell",
            type = DrinkType.TAP_BEER,
            description = "The original Czech Pilsner, featuring a crisp, clean taste with a delicate hop bitterness.",
            price = 5.99,
            volume = 500,
            abv = 4.4,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/175.jpg",
            brewery = Brewery("34", "Plzeňský Prazdroj", "Czech Republic", "Pilsen", "https://www.pilsnerurquell.com/"),
            beerIbu = 40,
            beerStyleId = "34",
            beerStyle = "Czech Pilsner"
        ),
        Beer(
            id = "35",
            name = "Guinness Draught",
            type = DrinkType.TAP_BEER,
            description = "A world-famous dry stout known for its creamy mouthfeel and roasty malt character.",
            price = 5.99,
            volume = 500,
            abv = 4.2,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/1246.jpg",
            brewery = Brewery("35", "Guinness Brewery", "Ireland", "Dublin", "https://www.guinness.com/"),
            beerIbu = 45,
            beerStyleId = "35",
            beerStyle = "Irish Dry Stout"
        ),
        Beer(
            id = "36",
            name = "Hoegaarden Witbier",
            type = DrinkType.TAP_BEER,
            description = "A classic Belgian wheat beer with hints of orange peel and coriander.",
            price = 5.49,
            volume = 500,
            abv = 4.9,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/1620.jpg",
            brewery = Brewery("36", "Brouwerij Hoegaarden", "Belgium", "Hoegaarden", "https://www.hoegaarden.com/"),
            beerIbu = 15,
            beerStyleId = "36",
            beerStyle = "Witbier"
        ),
        Beer(
            id = "37",
            name = "Krombacher Pils",
            type = DrinkType.TAP_BEER,
            description = "A crisp and refreshing German Pilsner with a balanced hoppy bitterness.",
            price = 5.49,
            volume = 500,
            abv = 4.8,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/2600.jpg",
            brewery = Brewery("37", "Krombacher Brauerei", "Germany", "Kreuztal", "https://www.krombacher.com/"),
            beerIbu = 35,
            beerStyleId = "37",
            beerStyle = "German Pilsner"
        ),
        Beer(
            id = "38",
            name = "Erdinger Weissbier",
            type = DrinkType.TAP_BEER,
            description = "A smooth and full-bodied wheat beer with fruity esters and a hint of spice.",
            price = 5.99,
            volume = 500,
            abv = 5.3,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/333.jpg",
            brewery = Brewery("38", "Erdinger Weissbräu", "Germany", "Erding", "https://int.erdinger.de/"),
            beerIbu = 12,
            beerStyleId = "38",
            beerStyle = "Hefeweizen"
        ),
        Beer(
            id = "39",
            name = "Schneider Weisse Original (TAP7)",
            type = DrinkType.TAP_BEER,
            description = "A rich, full-bodied wheat beer with banana and clove notes and a slightly malty sweetness.",
            price = 6.49,
            volume = 500,
            abv = 5.4,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/1270.jpg",
            brewery = Brewery("39", "Schneider Weisse", "Germany", "Kelheim", "https://schneider-weisse.de/"),
            beerIbu = 14,
            beerStyleId = "39",
            beerStyle = "Weizenbock"
        ),
        Beer(
            id = "40",
            name = "Chimay Blue",
            type = DrinkType.TAP_BEER,
            description = "A world-class Belgian Trappist strong ale with dark fruit, caramel, and spice flavors.",
            price = 7.49,
            volume = 500,
            abv = 9.0,
            imageUrl = "https://cdn.beeradvocate.com/im/beers/215.jpg",
            brewery = Brewery("40", "Chimay Brewery", "Belgium", "Chimay", "https://chimay.com/"),
            beerIbu = 35,
            beerStyleId = "40",
            beerStyle = "Belgian Strong Dark Ale"
        )
    )
}