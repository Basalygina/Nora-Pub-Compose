package com.blumenstreetdoo.nora_pub.domain.api

import com.blumenstreetdoo.nora_pub.domain.models.Beer
import kotlinx.coroutines.flow.Flow

interface MenuInteractor {
    fun getCraftList(): Flow<Pair<List<Beer>?, String?>>
    fun getBeerById(id: String): Pair<Beer?, String?>

}