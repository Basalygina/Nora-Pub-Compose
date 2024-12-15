package com.blumenstreetdoo.nora_pub.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Event(
    val id: String,
    val title: String,
    val description: String? = null,
    val dateTime: String? = null,
    val imageUrl: String? = null,
    val type: EventType? = null
) : Parcelable

enum class EventType {
    PARTY, DISCOUNT, HAPPY_HOUR
}