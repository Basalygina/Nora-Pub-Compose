package com.blumenstreetdoo.nora_pub.domain.api

import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.News
import com.blumenstreetdoo.nora_pub.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface EventNewsRepository {
    fun getEvents(): Flow<Resource<List<Event>>>
    fun getEventById(id: String): Resource<Event>

    fun getNews(): Flow<Resource<List<News>>>
    fun getNewsById(id: String): Resource<News>
}