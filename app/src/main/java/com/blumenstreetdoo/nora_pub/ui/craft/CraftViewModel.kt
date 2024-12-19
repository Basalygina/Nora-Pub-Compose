package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.blumenstreetdoo.nora_pub.domain.models.DrinkType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CraftViewModel : ViewModel() {
    private val _craftState = MutableStateFlow<CraftScreenState>(CraftScreenState.Loading)
    val craftState: StateFlow<CraftScreenState> = _craftState

    init {
        getFullBeerList()
    }

    private fun getFullBeerList() {
        _craftState.value = CraftScreenState.Loading
        viewModelScope.launch {
            delay(1000)
            val fullBeerList = getMockBeerList()
            _craftState.value = CraftScreenState.Content(fullBeerList)
        }
    }

    private fun getMockBeerList() : List<Beer> {
        return listOf(
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
                beerStyle = "IPA"
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
}