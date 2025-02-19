package com.blumenstreetdoo.nora_pub.ui.craft

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CraftFilterState(
    val searchQuery: String? = null,
    val breweryName: String? = null,
    val country: String? = null,
    val minAbv: Double? = null,
    val maxAbv: Double? = null,
    val minIbu: Double? = null,
    val maxIbu: Double? = null
) : Parcelable {
    val activeFilterCount: Int
        get() {
            var count = 0
            if (!searchQuery.isNullOrEmpty()) count++
            if (!breweryName.isNullOrEmpty()) count++
            if (!country.isNullOrEmpty()) count++
            if ((minAbv != null && minAbv > MIN_ABV) || (maxAbv != null && maxAbv < MAX_ABV)) count++
            if ((minIbu != null && minIbu > MIN_IBU) || (maxIbu != null && maxIbu < MAX_IBU)) count++
            return count
        }

    companion object {
        const val MIN_ABV = 0.0
        const val MAX_ABV = 16.0
        const val MIN_IBU = 0.0
        const val MAX_IBU = 100.0
    }
}