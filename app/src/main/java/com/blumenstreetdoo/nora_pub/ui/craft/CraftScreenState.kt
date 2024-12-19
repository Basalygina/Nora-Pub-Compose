package com.blumenstreetdoo.nora_pub.ui.craft

import com.blumenstreetdoo.nora_pub.domain.models.Beer

sealed class CraftScreenState {
    data object Loading : CraftScreenState()
    data class Content(
        val fullBeerList: List<Beer>,
    ) : CraftScreenState()
    data class Error(val message: String) : CraftScreenState()
}