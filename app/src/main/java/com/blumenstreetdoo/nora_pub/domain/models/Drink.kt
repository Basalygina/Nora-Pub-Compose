package com.blumenstreetdoo.nora_pub.domain.models

import java.io.Serializable

open class Drink(
    val id: String, // e.g. "3963"
    val name: String, // e.g. "Festina Peche"
    val type: DrinkType,
    val description: String?, // e.g. "A refreshing neo-BerlinerWeisse fermented with ..."
    val price: Double, // e.g. 5.5
    val volume: Int, // ml, e.g. 300
    val abv: Double, // alcohol by volume, e.g. 4.5
    val imageUrl: String?
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Drink) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Drink(id='$id', name='$name', type=$type, volume=$volume)"
    }
}

enum class DrinkType {
    TAP_BEER, CANNED_BEER, COCKTAIL, WINE, NON_ALCOHOLIC
}