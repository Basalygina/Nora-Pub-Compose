package com.blumenstreetdoo.nora_pub.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.domain.api.EventNewsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventNewsInteractor: EventNewsInteractor
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    init {
        Log.d("NTest", "HomeViewModel init")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val events = eventNewsInteractor.getEvents().first().first
                val news = eventNewsInteractor.getNews().first().first

                if (events != null && news!= null) {
                    _homeScreenState.value = HomeScreenState.Content(events, news)
                } else {
                    _homeScreenState.value = HomeScreenState.Error("Failed to load data")
                }
            } catch (e: Exception) { // add NoInternet
                _homeScreenState.value = HomeScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }
}