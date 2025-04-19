package com.blumenstreetdoo.nora_pub.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
    val SUBSCRIBE_NEWS = booleanPreferencesKey("subscribe_news")
    val SUBSCRIBE_EVENTS = booleanPreferencesKey("subscribe_events")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PHOTO_PATH = stringPreferencesKey("user_photo_path")
}