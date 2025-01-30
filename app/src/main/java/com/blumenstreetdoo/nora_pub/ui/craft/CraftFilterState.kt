package com.blumenstreetdoo.nora_pub.ui.craft


data class CraftFilterState(
    val searchQuery: String? = null,
    val breweryName: String? = null,
    val country: String? = null,
    val minAbv: Double? = null,
    val maxAbv: Double? = null,
    val minIbu: Double? = null,
    val maxIbu: Double? = null,
    val activeFilterCount: Int = 0
)