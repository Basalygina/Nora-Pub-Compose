package com.blumenstreetdoo.nora_pub.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationState.ReservationStatus.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReservationViewModel : ViewModel() {
    private val _reservationState = MutableStateFlow(ReservationState())
    val reservationState: StateFlow<ReservationState> = _reservationState.asStateFlow()

    fun setDate(date: String) {
        _reservationState.update {
            it.copy(
                date = date,
                status = Idle,
                statusVersion = it.statusVersion + 1
            )
        }
        validateForm()
    }

    fun setTime(time: String) {
        _reservationState.update {
            it.copy(
                time = time,
                status = Idle,
                statusVersion = it.statusVersion + 1
            )
        }
        validateForm()
    }

    fun setGuestCount(count: Int?) {
        _reservationState.update {
            it.copy(
                guests = count,
                status = Idle,
                statusVersion = it.statusVersion + 1
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val state = _reservationState.value

        val dateErrorRes = when {
            state.date == null -> R.string.please_select_date
            state.date.toDate() < getStartOfCurrentDay() -> R.string.date_in_past
            else -> null
        }

        val timeErrorRes = when {
            state.date != null && state.time == null -> R.string.please_select_time
            state.date != null && state.time != null && state.date.toDate() == getStartOfCurrentDay() && state.time.toTime() < currentTimeInMinutes() -> R.string.time_in_past
            state.time != null && !isValidTime(state.time) -> R.string.time_out_of_range
            else -> null
        }

        val guestErrorRes = if (state.guests == null || state.guests !in 1..10) {
            R.string.invalid_guest_count
        } else {
            null
        }

        _reservationState.update {
            it.copy(
                dateErrorRes = dateErrorRes,
                timeErrorRes = timeErrorRes,
                status = if (dateErrorRes != null || timeErrorRes != null || guestErrorRes != null) {
                    Error(messageRes = dateErrorRes ?: timeErrorRes ?: guestErrorRes)
                } else {
                    Idle
                },
                statusVersion = it.statusVersion + 1
            )
        }
    }

    fun submitReservation() {
        validateForm()
        val currentStatus = _reservationState.value.status
        if (currentStatus !is Error) {
            _reservationState.update {
                it.copy(
                    status = Loading,
                    statusVersion = it.statusVersion + 1
                )
            }
            // temporary simulation of network request
            viewModelScope.launch {
                try {
                    delay(1000)
                    _reservationState.update {
                        it.copy(
                            status = Success,
                            statusVersion = it.statusVersion + 1
                        )
                    }
                } catch (e: Exception) {
                    _reservationState.update {
                        it.copy(
                            status = Error(message = "Error: ${e.message}"),
                            statusVersion = it.statusVersion + 1
                        )
                    }
                }
            }
        }
    }

    // Additional functions
    private fun String.toDate(): Long =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)?.time ?: 0

    private fun String.toTime(): Int = split(":").let { it[0].toInt() * 60 + it[1].toInt() }

    private fun isValidTime(time: String): Boolean {
        val (hour, minute) = time.split(":").map { it.toInt() }
        return hour >= 16 && (hour < 23 || (hour == 23 && minute <= 30))
    }

    private fun getStartOfCurrentDay(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun currentTimeInMinutes(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    }
}