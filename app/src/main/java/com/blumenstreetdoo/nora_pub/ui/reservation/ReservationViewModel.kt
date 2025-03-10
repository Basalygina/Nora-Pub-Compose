package com.blumenstreetdoo.nora_pub.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {

    private val _reservationState = MutableStateFlow(ReservationState())
    val reservationState: StateFlow<ReservationState> = _reservationState.asStateFlow()

    fun setDate(date: String) = _reservationState.update { it.copy(date = date) }

    fun setTime(time: String) = _reservationState.update { it.copy(time = time) }

    fun setGuestCount(count: Int) = _reservationState.update { it.copy(guests = count) }

    fun submitReservation() {
        val state = _reservationState.value

        if (state.date.isNullOrEmpty() || state.time.isNullOrEmpty()) {
            val messageRes = R.string.please_select_date_time
            _reservationState.update { it.copy(status = ReservationState.Status.Error(messageRes = messageRes)) }
            return
        }
        _reservationState.update { it.copy(status = ReservationState.Status.Loading) }

        viewModelScope.launch {
            delay(2000) // Временная имитация запроса
            _reservationState.update { it.copy(status = ReservationState.Status.Success) }
        }
    }
}