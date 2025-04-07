package com.blumenstreetdoo.nora_pub.ui.reservation

data class ReservationState(
    val date: String? = null,
    val time: String? = null,
    val guests: Int? = 1,
    val status: ReservationStatus = ReservationStatus.Idle,
    val statusVersion: Int = 0, // Track status changes to force UI updates
    val dateErrorRes: Int? = null,
    val timeErrorRes: Int? = null,
    val guestsCountErrorRes: Int? = null,
) {
    sealed class ReservationStatus {
        data object Idle : ReservationStatus()
        data object Loading : ReservationStatus()
        data object Success : ReservationStatus()
        data class Error(val message: String? = null, val messageRes: Int? = null) : ReservationStatus()
    }
}