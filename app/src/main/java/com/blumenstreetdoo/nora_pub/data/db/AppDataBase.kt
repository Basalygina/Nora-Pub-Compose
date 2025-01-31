package com.blumenstreetdoo.nora_pub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blumenstreetdoo.nora_pub.data.db.dao.FavoriteBeerDao
import com.blumenstreetdoo.nora_pub.data.db.entity.FavoriteBeerEntity

@Database(version = 1, entities = [FavoriteBeerEntity::class], exportSchema = false)

abstract class AppDataBase : RoomDatabase() {

    abstract fun getFavoriteBeerDao(): FavoriteBeerDao
}