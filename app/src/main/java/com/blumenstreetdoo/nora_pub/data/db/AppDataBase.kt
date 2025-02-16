package com.blumenstreetdoo.nora_pub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blumenstreetdoo.nora_pub.data.db.dao.FavoriteBeerDao
import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity

@Database(version = 3, entities = [FavoriteBeerEntity::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteBeerDao(): FavoriteBeerDao
}