package com.blumenstreetdoo.nora_pub.domain.impl

import com.blumenstreetdoo.nora_pub.domain.api.MenuInteractor
import com.blumenstreetdoo.nora_pub.domain.api.MenuRepository
import com.blumenstreetdoo.nora_pub.domain.models.Beer
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuInteractorImpl(private val repository: MenuRepository) : MenuInteractor {
    override fun getCraftList(): Flow<Pair<List<Beer>?, String?>> {
        return repository.getCraftList().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getBeerById(id: String): Pair<Beer?, String?> {
        return when (val result = repository.getBeerById(id)) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}