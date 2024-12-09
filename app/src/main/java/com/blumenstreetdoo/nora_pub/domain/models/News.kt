package com.blumenstreetdoo.nora_pub.domain.models

data class News (
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val type: NewsType
)

enum class NewsType {
    NEW_ARRIVAL, OTHER_NEWS
}