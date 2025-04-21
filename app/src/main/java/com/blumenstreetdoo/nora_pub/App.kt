package com.blumenstreetdoo.nora_pub

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.blumenstreetdoo.nora_pub.di.dataModule
import com.blumenstreetdoo.nora_pub.di.interactorModule
import com.blumenstreetdoo.nora_pub.di.repositoryModule
import com.blumenstreetdoo.nora_pub.di.viewModelModule
import com.blumenstreetdoo.nora_pub.firebase.NoraFirebaseMessagingService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NoraFirebaseMessagingService.CHANNEL_ID,
            NoraFirebaseMessagingService.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = NoraFirebaseMessagingService.CHANNEL_DESCRIPTION
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }
}