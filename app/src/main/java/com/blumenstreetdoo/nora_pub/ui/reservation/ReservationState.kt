package com.blumenstreetdoo.nora_pub.ui.reservation

data class ReservationState(
    val date: String? = null,
    val time: String? = null,
    val guests: Int = 1,
    val status: Status = Status.Idle
) {
    sealed class Status {
        data object Idle : Status()
        data object Loading : Status()
        data object Success : Status()
        data class Error(val message: String? = null, val messageRes: Int? = null) : Status()
    }
}