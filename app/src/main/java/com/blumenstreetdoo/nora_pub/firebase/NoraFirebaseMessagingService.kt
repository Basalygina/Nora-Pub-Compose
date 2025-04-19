package com.blumenstreetdoo.nora_pub.firebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.blumenstreetdoo.nora_pub.MainActivity
import com.blumenstreetdoo.nora_pub.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject

class NoraFirebaseMessagingService : FirebaseMessagingService() {
    private val notificationManager: NotificationManager by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Received message: ${remoteMessage.data}")

        remoteMessage.notification?.let { notification ->
            val title = notification.title ?: getString(R.string.notification_default_title)
            val body = notification.body ?: getString(R.string.notification_default_body)
            showNotification(title, body, remoteMessage.data)
        } ?: Log.w(TAG, "No notification payload")
    }

    override fun onNewToken(token: String) {
        // Log new token
        Log.d(TAG, "Refreshed token: $token")
        sendTokenToServer(token)
    }

    private fun showNotification(title: String, body: String, data: Map<String, String>) {
        try {
            // Create PendingIntent for MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                data.forEach { (key, value) -> putExtra(key, value) }
            }
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Build notification
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.placeholder_nora)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            // Unique ID to avoid overwriting
            val notificationId = System.currentTimeMillis().toInt()
            notificationManager.notify(notificationId, notification)
        } catch (e: Exception) {
            Log.e(TAG, "Notification error: ${e.message}")
        }
    }

    private fun sendTokenToServer(token: String) {
        try {
            // TODO: Implement server API call for token
            Log.d(TAG, "Sending token to server: $token")
        } catch (e: Exception) {
            Log.e(TAG, "Token send error: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "NoraFCM"
        const val CHANNEL_ID = "nora_default_channel"
        const val CHANNEL_NAME = "Nora Notifications"
        const val CHANNEL_DESCRIPTION = "Default notification channel for Nora app"
    }
}