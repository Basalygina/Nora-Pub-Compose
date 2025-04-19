package com.blumenstreetdoo.nora_pub.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.blumenstreetdoo.nora_pub.data.preferences.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingsRepository(private val dataStore: DataStore<Preferences>) {

    val subscribeNewsFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.SUBSCRIBE_NEWS] ?: false }

    val subscribeEventsFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.SUBSCRIBE_EVENTS] ?: false }

    val usernameFlow: Flow<String> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_NAME] ?: "" }

    val isFirstLaunchFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.IS_FIRST_LAUNCH] ?: true }

    val photoPathFlow: Flow<String> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.USER_PHOTO_PATH] ?: "" }

    suspend fun saveNotificationSettings(subscribeNews: Boolean, subscribeEvents: Boolean, isFirstLaunch: Boolean) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.SUBSCRIBE_NEWS] = subscribeNews
            settings[PreferencesKeys.SUBSCRIBE_EVENTS] = subscribeEvents
            settings[PreferencesKeys.IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { settings ->
            settings[PreferencesKeys.USER_NAME] = username
        }
    }
}