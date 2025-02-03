package com.blumenstreetdoo.nora_pub

import android.app.Application
import com.blumenstreetdoo.nora_pub.di.dataModule
import com.blumenstreetdoo.nora_pub.di.interactorModule
import com.blumenstreetdoo.nora_pub.di.repositoryModule
import com.blumenstreetdoo.nora_pub.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}