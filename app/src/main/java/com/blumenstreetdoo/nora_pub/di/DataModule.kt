package com.blumenstreetdoo.nora_pub.di

import androidx.room.Room
import com.blumenstreetdoo.nora_pub.data.db.AppDataBase
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDataBase>().favoriteBeerDao() }

    single { Gson() }

}
