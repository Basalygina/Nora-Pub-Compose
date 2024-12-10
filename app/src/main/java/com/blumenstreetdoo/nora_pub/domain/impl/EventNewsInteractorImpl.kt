package com.blumenstreetdoo.nora_pub.domain.impl

import com.blumenstreetdoo.nora_pub.domain.api.EventNewsInteractor
import com.blumenstreetdoo.nora_pub.domain.api.EventNewsRepository
import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventNewsInteractorImpl(private val repository: EventNewsRepository) : EventNewsInteractor {
    override fun getEvents(): Flow<Pair<List<Event>?, String?>> {
        return repository.getEvents().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getEventById(id: String): Pair<Event?, String?> {
        return when (val result = repository.getEventById(id)) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }

    override fun getNews(): Flow<Pair<List<News>?, String?>> {
        return repository.getNews().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getNewsById(id: String): Pair<News?, String?> {
        return when (val result = repository.getNewsById(id)) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}