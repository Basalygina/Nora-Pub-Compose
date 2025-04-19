package com.blumenstreetdoo.nora_pub.di

import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.blumenstreetdoo.nora_pub.data.db.AppDataBase
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "app_database.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<AppDataBase>().favoriteBeerDao() }

    single { Gson() }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create {
            androidContext().applicationContext.preferencesDataStoreFile("user_settings")
        }
    }

    single<NotificationManager> {
        androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}
