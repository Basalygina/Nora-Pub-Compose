package com.blumenstreetdoo.nora_pub.ui.home

import com.blumenstreetdoo.nora_pub.domain.models.Event
import com.blumenstreetdoo.nora_pub.domain.models.News

sealed class HomeScreenState {
    object Loading: HomeScreenState()
    data class Content(
        val events: List<Event>,
        val news: List<News>
    ) : HomeScreenState()
    object NoInternet: HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}