package com.blumenstreetdoo.nora_pub.domain.models

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val dateTime: String,
    val imageUrl: String,
    val type: EventType
)

enum class EventType {
    PARTY, DISCOUNT, HAPPY_HOUR, NEW_ARRIVAL
}