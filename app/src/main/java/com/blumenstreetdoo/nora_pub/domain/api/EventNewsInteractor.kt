package com.blumenstreetdoo.nora_pub.domain.api

import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.News
import kotlinx.coroutines.flow.Flow

interface EventNewsInteractor {
    fun getEvents(): Flow<Pair<List<Event>?, String?>>
    fun getEventById(id: String): Pair<Event?, String?>

    fun getNews(): Flow<Pair<List<News>?, String?>>
    fun getNewsById(id: String): Pair<News?, String?>
}