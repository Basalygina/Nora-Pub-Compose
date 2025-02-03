package com.blumenstreetdoo.nora_pub.data.db

import androidx.room.TypeConverter
import com.blumenstreetdoo.nora_pub.domain.models.Brewery
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromBrewery(brewery: Brewery): String {
        return gson.toJson(brewery)
    }

    @TypeConverter
    fun toBrewery(json: String): Brewery {
        val type = object : TypeToken<Brewery>() {}.type
        return gson.fromJson(json, type)
    }
}