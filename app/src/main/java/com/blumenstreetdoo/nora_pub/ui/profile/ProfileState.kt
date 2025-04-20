package com.blumenstreetdoo.nora_pub.ui.profile

data class ProfileState(
    val isFirstLaunch: Boolean = true,
    val subscribeNews: Boolean = false,
    val subscribeEvents: Boolean = false,
    val username: String = "",
    val photoPath: String = "",
    val permissionGranted: Boolean = false
)