package com.blumenstreetdoo.nora_pub.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.blumenstreetdoo.nora_pub.data.preferences.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserSettingsRepository(private val dataStore: DataStore<Preferences>) {
    // upstream flow with error handling
    private val preferencesFlow: Flow<Preferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }

    // user info (username + saved photo URI)
    val userInfoFlow: Flow<UserInfo> = preferencesFlow
        .map { prefs ->
            UserInfo(
                username = prefs[PreferencesKeys.USER_NAME] ?: "",
                photoPath = prefs[PreferencesKeys.USER_PHOTO_PATH] ?: ""
            )
        }
        .distinctUntilChanged()

    // notification settings News & Events + firstLaunch flag
    val notificationSettingsFlow: Flow<NotificationSettings> = preferencesFlow
        .map { prefs ->
            NotificationSettings(
                subscribeNews = prefs[PreferencesKeys.SUBSCRIBE_NEWS] ?: false,
                subscribeEvents = prefs[PreferencesKeys.SUBSCRIBE_EVENTS] ?: false,
                isFirstLaunch = prefs[PreferencesKeys.IS_FIRST_LAUNCH] ?: true
            )
        }
        .distinctUntilChanged()

    // save methods
    suspend fun saveNotificationSettings(
        subscribeNews: Boolean,
        subscribeEvents: Boolean
    ) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.SUBSCRIBE_NEWS] = subscribeNews
            settings[PreferencesKeys.SUBSCRIBE_EVENTS] = subscribeEvents
        }
    }

    suspend fun saveIsFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.USER_NAME] = username
        }
    }

    suspend fun savePhotoPath(photoPath: String) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.USER_PHOTO_PATH] = photoPath
        }
    }

    data class UserInfo(
        val username: String,
        val photoPath: String
    )

    data class NotificationSettings(
        val subscribeNews: Boolean,
        val subscribeEvents: Boolean,
        val isFirstLaunch: Boolean
    )
}