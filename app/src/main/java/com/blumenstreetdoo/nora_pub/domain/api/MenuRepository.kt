package com.blumenstreetdoo.nora_pub.domain.api

import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getCraftList(): Flow<Resource<List<Beer>>>
    fun getBeerById(id: String): Resource<Beer>
}