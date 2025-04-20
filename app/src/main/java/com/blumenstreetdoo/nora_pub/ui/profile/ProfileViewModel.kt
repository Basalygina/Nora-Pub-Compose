package com.blumenstreetdoo.nora_pub.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.data.UserSettingsRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserSettingsRepository
) : ViewModel() {

    // UI state
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    // tracks notification-permission at runtime
    private val _permissionGranted = MutableStateFlow(false)

    init {
        // collect user info (username + photo)
        repository.userInfoFlow
            .onEach { info ->
                _state.update {
                    it.copy(
                        username = info.username,
                        photoPath = info.photoPath
                    )
                }
            }
            .launchIn(viewModelScope)

        // collect notification settings (news, events, first launch)
        repository.notificationSettingsFlow
            .onEach { settings ->
                _state.update {
                    it.copy(
                        subscribeNews = settings.subscribeNews,
                        subscribeEvents = settings.subscribeEvents,
                        isFirstLaunch = settings.isFirstLaunch
                    )
                }
            }
            .launchIn(viewModelScope)

        // Subscribe/unsubscribe to FCM “news” topic
        repository.notificationSettingsFlow
            .map { it.subscribeNews }
            .distinctUntilChanged()
            .combine(_permissionGranted) { subscribed, granted -> subscribed to granted }
            .handleTopic("news")

        // Subscribe/unsubscribe to FCM “events” topic
        repository.notificationSettingsFlow
            .map { it.subscribeEvents }
            .distinctUntilChanged()
            .combine(_permissionGranted) { subscribed, granted -> subscribed to granted }
            .handleTopic("events")
    }

    // called from Fragment when notification-permission result arrives
    fun onPermissionResult(granted: Boolean) {
        _permissionGranted.value = granted
        _state.update { it.copy(permissionGranted = granted) }
    }

    // user actions
    fun saveNotificationSettings(subscribeNews: Boolean, subscribeEvents: Boolean) {
        viewModelScope.launch {
            repository.saveNotificationSettings(
                subscribeNews = subscribeNews,
                subscribeEvents = subscribeEvents
            )
        }
    }

    fun markFirstLaunchCompleted() {
        viewModelScope.launch {
            repository.saveIsFirstLaunch(false)
        }
    }

    fun saveUsername(username: String) {
        if (username.isBlank()) return
        viewModelScope.launch {
            repository.saveUsername(username)
        }
    }

    fun savePhotoPath(path: String) {
        viewModelScope.launch {
            repository.savePhotoPath(path)
        }
    }

    private fun Flow<Pair<Boolean, Boolean>>.handleTopic(topic: String) =
        distinctUntilChanged()
            .onEach { (subscribed, granted) ->
                if (subscribed && granted)
                    FirebaseMessaging.getInstance().subscribeToTopic(topic)
                else
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            }
            .launchIn(viewModelScope)
}