package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CraftViewModel(
    private val menuInteractor: MenuInteractor
) : ViewModel() {
    private val _craftState = MutableStateFlow<CraftScreenState>(CraftScreenState.Loading)
    val craftState: StateFlow<CraftScreenState> = _craftState

    private val _Craft_filterState = MutableStateFlow(CraftFilterState())
    val craftFilterState: StateFlow<CraftFilterState> = _Craft_filterState

    private val originalBeerList = mutableListOf<Beer>()

    init {
        getFullBeerList()
    }

    private fun getFullBeerList() {
        _craftState.value = CraftScreenState.Loading
        viewModelScope.launch {
            try {
                val fullBeerList = menuInteractor.getCraftList().first().first
                if (fullBeerList != null) {
                    originalBeerList.clear()
                    originalBeerList.addAll(fullBeerList)
                    _craftState.value = CraftScreenState.Content(fullBeerList)
                } else {
                    _craftState.value = CraftScreenState.Error("Failed to load data")
                }
            } catch (e: Exception) { // add NoInternet
                _craftState.value = CraftScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateFilter(newFilter: CraftFilterState) {
        _Craft_filterState.value = newFilter
        applyFilters()
    }

    private fun applyFilters() {
        val filter = _Craft_filterState.value
        val filteredList = originalBeerList.filter { beer ->
            // searchEditText at CraftFragment
            (filter.searchQuery.isNullOrEmpty() || beer.name.contains(filter.searchQuery, ignoreCase = true)) &&
                    // parameters from CraftFilterFragment
                    (filter.breweryName.isNullOrEmpty() || beer.brewery.name.contains(filter.breweryName, ignoreCase = true)) &&
                    (filter.country.isNullOrEmpty() || beer.brewery.country.equals(filter.country, ignoreCase = true)) &&
                    // ABV
                    (filter.minAbv == null || beer.abv >= filter.minAbv) &&
                    (filter.maxAbv == null || beer.abv <= filter.maxAbv) &&
                    // IBU
                    (filter.minIbu == null || (beer.beerIbu != null && beer.beerIbu >= filter.minIbu)) &&
                    (filter.maxIbu == null || (beer.beerIbu != null && beer.beerIbu <= filter.maxIbu))
        }

        _craftState.value = CraftScreenState.Content(filteredList)
    }
}