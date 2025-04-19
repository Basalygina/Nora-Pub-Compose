package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.data.UserSettingsRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserSettingsRepository
) : ViewModel() {

    private val _permissionGranted = MutableStateFlow(false)
    fun onPermissionResult(granted: Boolean) {
        _permissionGranted.value = granted
    }
    val subscribeNewsFlow: Flow<Boolean> = repository.subscribeNewsFlow
    val subscribeEventsFlow: Flow<Boolean> = repository.subscribeEventsFlow

    init {
        // subscribe/unsubscribe based on flag + permissions
        viewModelScope.launch {
            combine(repository.subscribeNewsFlow, _permissionGranted) { s, g -> s to g }
                .handleTopic("news")
        }
        viewModelScope.launch {
            combine(repository.subscribeEventsFlow, _permissionGranted) { s, g -> s to g }
                .handleTopic("events")
        }
    }

    val isFirstLaunchFlow: Flow<Boolean> = repository.isFirstLaunchFlow
    val usernameFlow: Flow<String> = repository.usernameFlow
    val photoPathFlow: Flow<String> = repository.photoPathFlow

    fun saveNotificationSettings(subscribeNews: Boolean, subscribeEvents: Boolean) {
        viewModelScope.launch {
            repository.saveNotificationSettings(subscribeNews, subscribeEvents, isFirstLaunch = false)
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            if (username.isNotBlank()) {
                repository.saveUsername(username)
            }
        }
    }

    private fun Flow<Pair<Boolean,Boolean>>.handleTopic(topic: String) =
        distinctUntilChanged()
            .onEach { (subscribed, granted) ->
                if (subscribed && granted)
                    FirebaseMessaging.getInstance().subscribeToTopic(topic)
                else
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            }
            .launchIn(viewModelScope)
}