package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CraftViewModel(
    private val menuInteractor: MenuInteractor
) : ViewModel() {
    private val _craftState = MutableStateFlow<CraftScreenState>(CraftScreenState.Loading)
    val craftState: StateFlow<CraftScreenState> = _craftState

    private val _originalBeerList = MutableStateFlow<List<Beer>>(emptyList())
    val originalBeerList: StateFlow<List<Beer>> = _originalBeerList

    val defaultFilterState = CraftFilterState(
        minAbv = CraftFilterState.MIN_ABV,
        maxAbv = CraftFilterState.MAX_ABV,
        minIbu = CraftFilterState.MIN_IBU,
        maxIbu = CraftFilterState.MAX_IBU
    )
    private val _craftFilterState = MutableStateFlow(defaultFilterState)
    val craftFilterState: StateFlow<CraftFilterState> = _craftFilterState

    init {
        getFullBeerList()
    }

    private fun getFullBeerList() {
        _craftState.value = CraftScreenState.Loading
        viewModelScope.launch {
            try {
                val fullBeerList = menuInteractor.getCraftList().first().first
                if (fullBeerList != null) {
                    _originalBeerList.value = fullBeerList
                    applyFilters()
                } else {
                    _craftState.value = CraftScreenState.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _craftState.value = CraftScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getBeerById(beerId: String): Flow<Beer?> {
        return originalBeerList.map { list -> list.firstOrNull { it.id == beerId } }
    }

    fun updateFilter(newFilter: CraftFilterState) {
        _craftFilterState.value = newFilter
        applyFilters()
    }

    private fun applyFilters() {
        val filter = _craftFilterState.value
        val filteredList = _originalBeerList.value.filter { beer ->
            (filter.searchQuery.isNullOrEmpty() ||
                    beer.name.contains(filter.searchQuery, ignoreCase = true) ||
                    (beer.beerStyle?.contains(filter.searchQuery, ignoreCase = true) == true)) &&
                    (filter.breweryName.isNullOrEmpty() || beer.brewery.name.contains(
                        filter.breweryName,
                        ignoreCase = true
                    )) &&
                    (filter.country.isNullOrEmpty() || beer.brewery.country.equals(
                        filter.country,
                        ignoreCase = true
                    )) &&
                    (filter.minAbv == null || beer.abv >= filter.minAbv) &&
                    (filter.maxAbv == null || beer.abv <= filter.maxAbv) &&
                    (filter.minIbu == null || (beer.beerIbu != null && beer.beerIbu >= filter.minIbu)) &&
                    (filter.maxIbu == null || (beer.beerIbu != null && beer.beerIbu <= filter.maxIbu))
        }
        _craftState.value = CraftScreenState.Content(filteredList)
    }

    fun resetFilter() {
        _craftFilterState.value = defaultFilterState
        applyFilters()
    }
}