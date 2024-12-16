package com.blumenstreetdoo.nora_pub.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: String,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val type: NewsType
) : Parcelable

enum class NewsType {
    NEW_ARRIVAL, OTHER_NEWS
}