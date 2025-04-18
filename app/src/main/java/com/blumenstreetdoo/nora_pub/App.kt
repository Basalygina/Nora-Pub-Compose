package com.blumenstreetdoo.nora_pub

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.blumenstreetdoo.nora_pub.di.dataModule
import com.blumenstreetdoo.nora_pub.di.interactorModule
import com.blumenstreetdoo.nora_pub.di.repositoryModule
import com.blumenstreetdoo.nora_pub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}