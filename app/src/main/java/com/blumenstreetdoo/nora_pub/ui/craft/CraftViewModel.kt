package com.blumenstreetdoo.nora_pub.ui.craft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CraftViewModel(
    private val menuInteractor: MenuInteractor
) : ViewModel() {
    private val _craftState = MutableStateFlow<CraftScreenState>(CraftScreenState.Loading)
    val craftState: StateFlow<CraftScreenState> = _craftState

    init {
        getFullBeerList()
    }

    private fun getFullBeerList() {
        _craftState.value = CraftScreenState.Loading
        viewModelScope.launch {
            try {
                val fullBeerList = menuInteractor.getCraftList().first().first
                if (fullBeerList != null) {
                    _craftState.value = CraftScreenState.Content(fullBeerList)
                } else {
                    _craftState.value = CraftScreenState.Error("Failed to load data")
                }
            } catch (e: Exception) { // add NoInternet
                _craftState.value = CraftScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }
}