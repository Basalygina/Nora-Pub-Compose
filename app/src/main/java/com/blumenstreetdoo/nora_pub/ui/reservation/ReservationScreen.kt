package com.blumenstreetdoo.nora_pub.ui.reservation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.ui.common.LoadingState
import com.blumenstreetdoo.nora_pub.ui.reservation.ReservationState.ReservationStatus
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewModel: ReservationViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.reservationState.collectAsState()
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Date Picker Button
        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = NoraColors.onBackground
            ),
        ) {
            Text(state.date ?: stringResource(R.string.select_date), style = NoraTypography.labelLarge)
        }

        // Time Picker Button
        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = NoraColors.onBackground
            ),
            enabled = state.date != null // Time selection requires a date
        ) {
            Text(state.time ?: stringResource(R.string.select_time), style = NoraTypography.labelLarge)
        }

        // Guest Count Input
        OutlinedTextField(
            value = state.guests.toString(),
            onValueChange = { viewModel.setGuestCount(it.toIntOrNull() ?: 1) },
            label = { Text(stringResource(R.string.guest_count)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Submit Button
        Button(
            onClick = { viewModel.submitReservation() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.status !is ReservationStatus.Error && state.date != null && state.time != null && state.guests != null
        ) {
            Text(stringResource(R.string.confirm_reservation))
        }

        // Display loading or success
        when (state.status) {
            is ReservationStatus.Loading -> LoadingState()
            is ReservationStatus.Success -> {
                val message = stringResource(R.string.reservation_success)
                LaunchedEffect(state.status) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }

            is ReservationStatus.Error -> {
                val errorState = state.status as ReservationStatus.Error
                val errorMessage = errorState.message ?: errorState.messageRes?.let { stringResource(it) }
                    ?: "Unknown error"
                LaunchedEffect(state.status) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            else -> {}
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                .format(
                                    datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                                )
                            viewModel.setDate(selectedDate)
                        }
                    ) { Text("OK") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Custom Time Picker Dialog
        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                            val selectedTime = String.format("%d:%02d", timePickerState.hour, timePickerState.minute)
                            viewModel.setTime(selectedTime)
                        }
                    ) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
                },
                title = { Text("Select Time") },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}